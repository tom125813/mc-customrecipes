# CustomRecipes
An open-source minecraft plugin which allows admins to create custom items with ease. It comes with an additonal menu which displays all craftable custom items and displays their crafting format.


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
      unbreaking: 9
    recipe:
      format:
      - '#'
      - '#'
      - /
      materials:
        '#': red dye
        /: redstone torch
    flags:
      hide_attributes: true
  emerald_dagger:
    displayname: '&2Emerald Dagger'
    lore:
    - '&aMade using the finest'
    - '&aunderground &ndelicacies&a'
    - ''
    - '&a&lUNCOMMON'
    material: mangrove propagule
    enchantments:
        sharpness: 4
    recipe:
      format:
      - 'i'
      - 'i'
      - 'a'
      materials:
        'i': emerald
        'a': bamboo
    flags:
      hide_attributes: true
```

>**Warning:** Currently in development and will likely change

## API
| Method                                              | Functionality                        
|-----------------------------------------------------|------------------------------------------------|
|`CustomRecipes#newRecipe(Recipe, ItemStack)`         |Create a new crafting formula                   |
|`CustomRecipes#removeRecipe(Recipe)`                 |Remove a formula and it's resulting item        |

>**Warning:** The API for this plugin is currently incomplete and unusable. It is recommended to wait until official release before attemtpting to integrate this plugin with your own.
