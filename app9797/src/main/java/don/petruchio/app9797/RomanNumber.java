package don.petruchio.app9797;

public class RomanNumber {


    final static char[][] alf=
            {
                    {'I','V'},
                    {'X','L'},
                    {'C','D'},
                    {'M','W'}
            };


    public static String toRoman(int number)
    {
        if (number > 3999)
        {
            number%=4000;
        }

        String res = "";

        String num = number +"";
        for(int i = num.length()-1, k = 0; i>=0;i--,k++)
        {
            switch(num.charAt(i))
            {
                case '1': res=alf[k][0]+res;
                    break;
                case '2': res=alf[k][0]+""+alf[k][0]+res;
                    break;
                case '3': res=alf[k][0]+""+alf[k][0]+""+alf[k][0]+res;
                    break;
                case '4': res=alf[k][0]+""+alf[k][1]+res;
                    break;
                case '5': res=alf[k][1]+""+res;
                    break;
                case '6': res=alf[k][1]+""+alf[k][0]+res;
                    break;
                case '7': res=alf[k][1]+""+alf[k][0]+""+alf[k][0]+res;
                    break;
                case '8': res=alf[k][1]+""+alf[k][0]+""+alf[k][0]+""+alf[k][0]+res;
                    break;
                case '9': res=alf[k][0]+""+alf[k+1][0]+res;
                    break;

            }
        }
        return res;
    }

}
