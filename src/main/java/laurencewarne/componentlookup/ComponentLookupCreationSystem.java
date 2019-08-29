package laurencewarne.componentlookup;

import java.lang.reflect.Field;
import java.util.function.Function;

import com.artemis.Aspect;
import com.artemis.BaseSystem;
import com.artemis.Component;
import com.artemis.ComponentMapper;
import com.artemis.EntitySubscription.SubscriptionListener;
import com.artemis.utils.IntBag;
import com.artemis.utils.IntBagIterator;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.ObjectIntMap;

public class ComponentLookupCreationSystem extends BaseSystem {

    @Override
    public void processSystem() {
	
    }

    public <C extends Component, T> ObjectIntMap<T> createLookup(
	final String fieldName,
	final Class<C> componentClass,
	final Class<T> fieldClass
    ) throws NoSuchFieldException, SecurityException {
	final Field field = componentClass.getField(fieldName);
	if (field.getType().equals(fieldClass)) {
	    final LookupSubscription<C, T> sub = new LookupSubscription<>(
		world.getMapper(componentClass), null
	    );
	    world.getAspectSubscriptionManager().get(Aspect.all(componentClass))
		.addSubscriptionListener(sub);
	    return sub.getLookup();
	}
	else {
	    throw new IllegalArgumentException();
	}
    }

    private static class LookupSubscription<C extends Component, T>
	implements SubscriptionListener {

	private final ComponentMapper<C> m;
	/** Gets the lookup field from a component.*/
	private final Function<C, T> retrievalFunction;
	private final ObjectIntMap<T> lookup;
	private final IntMap<T> reverseLookup;

	public LookupSubscription(
	    final ComponentMapper<C> m,
	    final Function<C, T> retrievalFunction
	) {
	    this.m = m;
	    this.retrievalFunction = retrievalFunction;
	    this.lookup = new ObjectIntMap<>();
	    this.reverseLookup = new IntMap<>();
	}

	private ObjectIntMap<T> getLookup() {
	    return lookup;
	}

	@Override
	public void inserted(IntBag entities) {
	    final IntBagIterator it = new IntBagIterator(entities);
	    while (it.hasNext()) {
		final int entity = it.next();
		final T key = retrievalFunction.apply(m.get(entity));
		lookup.put(key, entity);
		reverseLookup.put(entity, key);
	    }
	}

	@Override
	public void removed(IntBag entities) {
	    final IntBagIterator it = new IntBagIterator(entities);
	    while (it.hasNext()) {
		final int entity = it.next();
		final T key = reverseLookup.get(entity);
		lookup.remove(key, -1);
		reverseLookup.remove(entity);
	    }
	}
	
    }
}
