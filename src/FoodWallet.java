
/**
 * A class to manage your money for food
 */
public class FoodWallet {

    private int foodPrice;
    private int money;

    //no-arg constructor
    public FoodWallet(){
        foodPrice = 33;
        money = 0;
    }

    //constructor
    public FoodWallet(int initialMoney) {
        money = initialMoney;
    }

    /**
     * @param depositAmount the amount of increment
     */
    public void depositMoney(int depositAmount) {
        money += depositAmount;
    }

    /**
     * decrements money by food price
     * @return returns true if there is enough money, false otherwise
     */
    public boolean eat(){
        if(money >= foodPrice) {
            money -= foodPrice;
            return true;
        }
        else {
            return false;
        }
    }

    //accessors
    public int getFoodPrice() {
        return foodPrice;
    }
    public int getMoney() {
        return money;
    }

    //setter
    public void setFoodPrice(int foodPrice) {
        this.foodPrice = foodPrice;
    }

    //overrides toString
    public String toString(){
        return "There is " + money + " TL in the food wallet.";
    }

}
