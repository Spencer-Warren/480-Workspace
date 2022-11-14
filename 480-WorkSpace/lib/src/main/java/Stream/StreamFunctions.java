package Stream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.ToLongFunction;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamFunctions {
	public static final BiFunction<Stream<String>,Character,Stream<String>>          a = null;
	public static final UnaryOperator<Stream<Integer>>                               b = null;
	public static final Function<Stream<Character>,String>                           c = null;
	public static final Function<Stream<Integer[]>,Stream<Integer>>                  d = null;
	public static final Function<IntStream,Integer>                                  e = null;
	public static final UnaryOperator<Stream<Person>>                                f = null;
	public static final ToLongFunction<Stream<Person>>                               g = null;
	public static final Function<Stream<Person>,List<String>>                        h = null;
	public static final Function<Stream<Person>,Map<Person.Gender,List<Person>>>     i = null;
	public static final Function<Stream<Person>,Map<Integer,List<String>>>           j = null;
	public static final Function<Stream<Person>,Map<Integer,Long>>                   k = null;
	public static final Function<Stream<Person>,Map<Person.Gender,Optional<Person>>> l = null;
	public static final BiFunction<Stream<Person>,Integer,Map<Boolean,List<String>>> m = null;
	
	private StreamFunctions() {
	}
}
