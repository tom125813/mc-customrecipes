# CustomRecipes
An open-source minecraft plugin which allows admins to create custom items with ease. It comes with an additonal menu which displays all craftable custom items and displays their crafting format.

## Usage
| Commands                                            | Functionality
|-----------------------------------------------------|------------------------------------------------|
|`/customrecipes list`                                |Displays a list of all available custom items   |
|`/customrecipes give <player> <item_identifier>`     |Gives the target player the defined item        |
> **Warning:** These commands will most definitely be changing soon

## Configuration file
```yaml
recipes:
  crimson_blade:
    displayname: '&4Crimson Blade'
    lore:
    - '&cThis sword comes from the'
    - '&cdepths of the nether forests..'
    - ''
    - '&4&lRARE'
    material: netherite sword
    enchantments:
      sharpness: 10
      fire aspect: 2
    recipe:
      format:
      - ' # '
      - ' # '
      - ' / '
      materials:
        '#': red dye
        '/': redstone torch
    flags:
      hide attributes: true
      hide enchantments: false
  emerald_dagger:
    displayname: '&2Emerald Dagger'
    lore:
    - '&aMade using the finest'
    - '&aunderground &ndelicacies&a'
    - ''
    - '&a&lUNCOMMON'
    material: wooden sword
    enchantments:
        sharpness: 4
    recipe:
      format:
      - ' i '
      - ' i '
      - ' a '
      materials:
        'i': emerald
        'a': bamboo
    flags:
      hide attributes: true
      hide enchantments: false
```

>**Warning:** Currently in development and will likely change

## API
| Method                                              | Functionality                        
|-----------------------------------------------------|------------------------------------------------|
|`CustomRecipes#newRecipe(Recipe, ItemStack)`         |Create a new crafting formula                   |
|`CustomRecipes#removeRecipe(Recipe)`                 |Remove a formula and it's resulting item        |

>**Warning:** The API for this plugin is currently incomplete and unusable. It is recommended to wait until official release before attemtpting to integrate this plugin with your own.

## Developer Notes

| Classes to recode                                   | Reason                        
|-----------------------------------------------------|------------------------------------------------|
|`CommandCustomRecipes.class`                         | Poorly written - was in a rush                 |

### Todo:
    Add inventory pages
    Custom model functionality?
