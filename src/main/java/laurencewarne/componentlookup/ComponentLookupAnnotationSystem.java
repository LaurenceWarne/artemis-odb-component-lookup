package laurencewarne.componentlookup;

import java.lang.reflect.Field;

import com.artemis.BaseSystem;
import com.badlogic.gdx.utils.ObjectIntMap;

import laurencewarne.componentlookup.annotations.FieldLookup;

@SuppressWarnings("unchecked")
public class ComponentLookupAnnotationSystem extends BaseSystem {

    private ComponentLookupCreationSystem lookupCreatorSystem;

    @Override
    public void initialize() {
	for (BaseSystem system : world.getSystems()) {
	    initLookupsForSystem(system);
	}
    }

    private void initLookupsForSystem(final BaseSystem system) {
	for (Field field : system.getClass().getDeclaredFields()) {
	    final FieldLookup lookup = field.getAnnotation(FieldLookup.class);
	    if (lookup != null) {
		final ObjectIntMap map;
		try {
		    map = lookupCreatorSystem.createLookup(
			lookup.componentField(),
			lookup.targetClass(),
			field.getType()
		    );
		} catch (NoSuchFieldException | SecurityException e) {
		    throw new IllegalStateException(
			"Could not find or access field: " + e.getMessage()
		    );
		}
		// inject map into field
		injectMapIntoSystem(field, system, map);
	    }
	}
    }

    private void injectMapIntoSystem(
	final Field field, final BaseSystem system, final ObjectIntMap map
    ) {
	try {
	    field.setAccessible(true);
	    field.set(system, map);
	} catch (IllegalArgumentException | IllegalAccessException e) {
	    throw new IllegalArgumentException(
		"Could not inject lookup map in system: " + e.getMessage()
	    );
	}
    }

    @Override
    public void processSystem() {

    }
}
