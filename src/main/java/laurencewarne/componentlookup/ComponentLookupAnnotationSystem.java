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
		final ObjectIntMap lookupMap = getLookupForField(lookup);
		// inject map into field
		injectMapIntoSystem(field, system, lookupMap);
	    }
	}
    }

    private ObjectIntMap<?> getLookupForField(final FieldLookup lookup) {
	if (lookup == null) {
	    throw new IllegalArgumentException("lookup cannot be null!");
	}
	final ObjectIntMap<?> map;
	final String targetFieldName = lookup.field();
	try {
	    map = lookupCreatorSystem.createLookup(
		targetFieldName,
		lookup.component(),
		lookup.component().getDeclaredField(targetFieldName).getType()
	    );
	} catch (NoSuchFieldException | SecurityException e) {
	    throw new IllegalStateException(
		"Could not find or access field: " + e.getMessage()
	    );
	}
	return map;
    }

    private void injectMapIntoSystem(
	final Field field, final BaseSystem system, final ObjectIntMap map
    ) {
	field.setAccessible(true);
	try {
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
