package Stream;
import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class StreamCreation {
	public static final DoubleStream                          generate = null;
	public static final Stream<Character>                     iterate  = null;
	public static final Stream<Boolean>                       of       = null;
	public static final DoubleStream.Builder                  builder  = null;
	public static final BiFunction<Integer,Integer,IntStream> range    = null;
	public static final Function<long[], LongStream>          stream   = null;
	public static final BinaryOperator<Stream<String>>        concat   = null;
	
	private StreamCreation() { }
}
