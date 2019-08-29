package laurencewarne.componentlookup;

import java.lang.reflect.Field;

import com.artemis.BaseSystem;

import laurencewarne.componentlookup.annotations.FieldLookup;

public class ComponentLookupAnnotationSystem extends BaseSystem {

    private ComponentLookupCreationSystem lookupCreatorSystem;

    @Override
    public void initialize() {
	for (BaseSystem system : world.getSystems()) {
	    for (Field field : system.getClass().getDeclaredFields()) {
		final FieldLookup lookup = field.getAnnotation(FieldLookup.class);
		if (lookup != null) {
		    try {
			lookupCreatorSystem.createLookup(lookup.componentField(), lookup.targetClass(), field.getType());
		    } catch (NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		    }
		    // Create lookup maps
		    // inject map into field
		}

	    }
	}
    }

    @Override
    public void processSystem() {

    }
}
