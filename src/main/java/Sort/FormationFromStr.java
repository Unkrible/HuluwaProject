package Sort;

import Field.Field;
import Model.Position.Position;
import Model.Position.PositionXY;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FormationFromStr {
    private static Map<String, String> formationStrs = new HashMap<String, String>();

    static {
        formationStrs.put("长蛇", "*\n*\n*\n*\n*\n*\n*\n*\n");
        formationStrs.put("雁行",
                        "*\n" +
                        " *\n" +
                        "  *\n" +
                        "   *\n" +
                        "    *\n" +
                        "     *\n" +
                        "      *\n");
        formationStrs.put("锋矢",
                        " *\n" +
                        "******\n" +
                        " *\n");
    }

    public ArrayList<Position> formation(String name){
        ArrayList<Position> formations = new ArrayList<Position>();
        int x=0, y=0;
        String formationStr = formationStrs.get(name);
        if(formationStr == null)
            return null;
        char item = 0;
        for(int i=0; i<formationStr.length(); i++){
            item = formationStr.charAt(i);
            switch (item){
                case '*':
                    formations.add(new PositionXY(x, y, Field.FIELD_SIZE_OF_X));
                    ++y;
                    break;
                case '\n':
                    ++x;
                    y = 0;
                    break;
                case ' ':
                    ++y;
                    break;
            }
        }
        return formations;
    }
}
