package laurencewarne.componentlookup;

import com.artemis.ArtemisPlugin;
import com.artemis.WorldConfigurationBuilder;

public class ComponentLookupPlugin implements ArtemisPlugin {

    @Override
    public void setup(final WorldConfigurationBuilder setup) {
	setup.with(
	    new ComponentLookupAnnotationSystem(),
	    new ComponentLookupCreationSystem()
	);
    }
}
