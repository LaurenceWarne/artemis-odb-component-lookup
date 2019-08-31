package laurencewarne.componentlookup;

import com.artemis.BaseSystem;
import com.artemis.ComponentMapper;
import com.badlogic.gdx.utils.ObjectIntMap;

import laurencewarne.componentlookup.annotations.FieldLookup;

public class SystemInit extends BaseSystem {

    @FieldLookup(component=ComponentA.class, field="name")
    public ObjectIntMap<String> lookup;
    private ComponentMapper<ComponentA> m;

    @Override
    public void initialize() {
	m.create(world.create());
    }

    @Override
    public void processSystem() {
	
    }
    
}

