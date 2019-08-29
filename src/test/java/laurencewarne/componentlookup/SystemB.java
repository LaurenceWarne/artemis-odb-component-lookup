package laurencewarne.componentlookup;

import com.artemis.BaseSystem;
import com.badlogic.gdx.utils.IntMap;

import laurencewarne.componentlookup.annotations.FieldLookup;

public class SystemB extends BaseSystem {

    @FieldLookup(targetClass=ComponentA.class, componentField="Age")
    public IntMap<Integer> lookup;

    @Override
    public void processSystem() {
	
    }
}


