package laurencewarne.componentlookup;

import java.lang.reflect.Field;

import com.artemis.Aspect;
import com.artemis.BaseSystem;
import com.artemis.Component;
import com.badlogic.gdx.utils.ObjectIntMap;
import com.badlogic.gdx.utils.ObjectMap;

@SuppressWarnings("unchecked")
public class ComponentLookupCreationSystem extends BaseSystem {

    private final ObjectMap<LookupKey, ObjectIntMap<?>> lookupCache =
	new ObjectMap<>();

    @Override
    public void processSystem() {
	
    }

    <C extends Component, T> ObjectIntMap<T> createLookup(
	final String fieldName,
	final Class<C> componentClass,
	final Class<T> fieldClass
    ) throws NoSuchFieldException, SecurityException {
	final Field field = componentClass.getDeclaredField(fieldName);
	field.setAccessible(true);
	if (!field.getType().equals(fieldClass)) {
	    throw new IllegalArgumentException(
		"Error for field: '" + fieldName + "', field type is: " +
		field.getType() + " but expected type: " + fieldClass
	    );
	}
	final LookupKey key = new LookupKey(componentClass, fieldName);
	if (lookupCache.containsKey(key)) {
	    return (ObjectIntMap<T>) lookupCache.get(key);
	}
	else {
	    final ObjectIntMap<T> map = createLookup(
		field, componentClass, fieldClass
	    );
	    lookupCache.put(key, map);
	    return map;
	}
    }

    <C extends Component, T> ObjectIntMap<T> createLookup(
	final Field field,
	final Class<C> componentClass,
	final Class<T> fieldClass
    ) {
	final LookupSubscription<C, T> sub = new LookupSubscription<>(
	    world.getMapper(componentClass),
	    component -> {
		try {
		    return (T) field.get(component);
		} catch (IllegalArgumentException | IllegalAccessException | ClassCastException e) {  // shouldn't happen
		    throw new IllegalStateException();
		}
	    }
	);
	// Subscribe to aspect via world object
	world.getAspectSubscriptionManager().get(Aspect.all(componentClass))
	    .addSubscriptionListener(sub);
	return sub.getLookup();
    }

    private final static class LookupKey {
	private final Class<? extends Component> componentClass;
	private final String fieldName;

	public LookupKey(
	    final Class<? extends Component> componentClass,
	    final String fieldName
	) {
	    this.componentClass = componentClass;
	    this.fieldName = fieldName;
	}

	public Class<? extends Component> getComponentClass() {
	    return componentClass;
	}

	public String getFieldName() {
	    return fieldName;
	}

	@Override
	public boolean equals(final Object obj) {
	    if (obj != null && obj instanceof LookupKey) {
		LookupKey key = (LookupKey)obj;
		return componentClass.equals(key.getComponentClass()) &&
		    fieldName.equals(key.getFieldName());
	    }
	    else {
		return false;
	    }
	}

	@Override
	public int hashCode() {
	    return componentClass.hashCode() + fieldName.hashCode();
	}
    }
}
