package laurencewarne.componentlookup;

import java.lang.reflect.Field;
import java.util.function.Function;

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
	    return new ObjectIntMap<>();
	}

	return null;
    }

    private static class LookupSubscription<C extends Component, T>
	implements SubscriptionListener {

	private final ComponentMapper<C> m;
	private final Function<C, T> retrievalFunction;
	private final ObjectIntMap<C> lookup;
	private final IntMap<C> reverseLookup;

	public LookupSubscription(
	    final ComponentMapper<C> m,
	    final Function<C, T> retrievalFunction
	) {
	    this.m = m;
	    this.retrievalFunction = retrievalFunction;
	    this.lookup = new ObjectIntMap<>();
	    this.reverseLookup = new IntMap<>();
	}

	private ObjectIntMap<C> getLookup() {
	    return lookup;
	}

	@Override
	public void inserted(IntBag entities) {

	}

	@Override
	public void removed(IntBag entities) {
			
	}
	
    }
}
