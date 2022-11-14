package Stream;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

public class StreamIntermediate {
	public static final UnaryOperator<Stream<Integer>>                      negative   = null;
	public static final UnaryOperator<Stream<Integer>>                      squared    = null;			
	public static final UnaryOperator<Stream<Character>>                    unique     = null;
	public static final Function<String,Stream<Character>>                  lowercase  = null;
	public static final Function<Stream<String>,Optional<String>>           longest    = null;
	public static final BiFunction<Stream<Integer>,Integer,Stream<Integer>> higherThan = null;
	
	private StreamIntermediate() { }
}
