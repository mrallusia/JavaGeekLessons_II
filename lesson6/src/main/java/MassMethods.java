public class MassMethods {

    /**
     * Метод должен вернуть новый массив, который получен путем вытаскивания
     * из исходного массива элементов, идущих после последней четверки.
     */

    public void getMassAfterFour (int[] mass) {
        if (!isMassEmpty(mass) && isFourInMass(mass)) { //проходим если в массиве хоть чтото есть

            if(mass[mass.length-1] != 4) { // проверим что последний элемент не является цифрой 4

                // получим позицию последней цифры 4
                int index = 0;
                for (int i = 0; i < mass.length; i++) {
                    if (mass[i] == 4) {
                        index = i+1;
                    }
                }

                // перекинем всё после последней 4 в новый массив
                int[] massAfterFour = new int[mass.length - index];
                for (int i = 0; i < massAfterFour.length; i++) {
                    massAfterFour[i] = mass[index + i];
                }

                //for (int i = 0; i < massAfterFour.length; i++) {
                //    System.out.print(massAfterFour[i] + " ");
                //}
            } else {
                throw new RuntimeException("В массиве цифра '4' в самом конце");
            }
        }

    }

    public boolean isMassEmpty(int[] mass) {
        return mass.length == 0;
    }

    public boolean isFourInMass(int[] mass) {
        for (int o : mass) {
            if (o == 4) {
                return true;
            }
        }
        throw new RuntimeException("В массиве нет цифр '4' ");
    }

    /**
     * Написать метод, который проверяет состав массива из чисел 1 и 4.
     * Если в нем нет хоть одной четверки или единицы, то метод вернет false;
     */

    public boolean isOneAndFourInMass(int[] mass) {
       boolean resultO = false;
       boolean resultF = false;

        for (int o : mass) {
            if (o == 1) {
                resultO = true;
            } else if (o != 4){
                return false;
            }
        }

        for (int o : mass) {
            if (o == 4) {
                resultF = true;
            } else if (o != 1){
                return false;
            }
        }

        return resultO && resultF;
    }

}