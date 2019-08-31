# artemis-odb-component-lookup
[![Build Status](https://travis-ci.org/LaurenceWarne/artemis-odb-component-lookup.svg?branch=master)](https://travis-ci.org/LaurenceWarne/second-space)

artemis-odb-component-lookup is an artemis-odb plugin which allows access of components through their attributes.

## Usage

```java
...
final WorldConfigurationBuilder setup = new WorldConfigurationBuilder();
setup.with(new ComponentLookupPlugin());
// Register other systems and plugins
...
```

Now take some component:

```java
public class MonsterTemplate extends Component {
    private String name;
    ...
}
```

Then we can use the ```@FieldLookup``` annotation to obtain player components from their name:

```java
@All(Spawn.class)
public class MonsterSpawningSystem extends IteratingSystem {

	private ComponentMapper<Spawn> mSpawn;
	private ComponentMapper<MonsterTemplate> mTemplate;

	// Injected on world intialization
	@FieldLookup(component=MonsterTemplate.class, field="name")
	private ObjectIntMap<String> templateLookup;

    @Override
    public void process(int id) {
        final Spawn spawn = mSpawn.get(id);
        final int templateId = templateLookup.get(spawn.getTemplateName());
        final MonsterTemplate template = mTemplate.get(templateId);
        // do something with component
    }
}
```

## Getting Started

### Gradle

```groovy
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}

dependencies {
    implementation "com.github.laurencewarne:artemis-odb-component-lookup:-SNAPSHOT"
}
```
