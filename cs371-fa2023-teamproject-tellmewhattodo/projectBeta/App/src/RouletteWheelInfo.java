
class RouletteWheelInfo {
    private String name;
    private RouletteWheel wheel;

    public RouletteWheelInfo(String name, RouletteWheel sampleWheel) {
        this.name = name;
        this.wheel = new RouletteWheel();
    }

    public String getName() {
        return name;
    }

    public RouletteWheel getWheel() {
        return wheel;
    }

    @Override
    public String toString() {
        return name; // Return the wheel's name for display
    }
}