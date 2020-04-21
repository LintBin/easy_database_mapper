package com.wecan.generation.spell;

import com.wecan.data.Table;

public interface IMapperXmlSpell {
    String getMapperXmlName(Table table);
    String getMapperContent(Table table);
}
