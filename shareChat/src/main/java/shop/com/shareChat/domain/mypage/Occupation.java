package shop.com.shareChat.domain.mypage;

public enum Occupation {
    
    개발자(0),
    디자이너(1),
    기획자(2),
    기타(3);

    private final int occupation;

    Occupation(int occupation) {
        this.occupation = occupation;
    }

    public int getOccupationValue() {
        return occupation;
    }

    public static String getOccupationByValue(int value) {
        for (Occupation occupation : Occupation.values()) {
            if (occupation.getOccupationValue() == value) {
                return String.valueOf(occupation);
            }
        }
        throw new IllegalArgumentException("Invalid occupation value: " + value);
    }
}
