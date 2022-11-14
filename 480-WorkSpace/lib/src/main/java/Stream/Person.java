package Stream;
public record Person(String name, int age, int zip, Gender gender) {
	public enum Gender { MALE, FEMALE }
}
