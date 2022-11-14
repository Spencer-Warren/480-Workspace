package Stream;
import static com.google.common.truth.Truth.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.ToLongFunction;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import Stream.Person;
import Stream.StreamFunctions;

public class StreamFunctionsTest {
	@ParameterizedTest
	@MethodSource("dataA")
	void testA(List<String> input, char letter, List<String> expected) {
		BiFunction<Stream<String>,Character,Stream<String>> function = StreamFunctions.a;
		assertThat                                        ( function ).isNotNull();
		
		var         stream = function.apply( input.stream(), letter );
		assertThat( stream ).isNotNull();
		var         actual = stream.collect( Collectors.toList() );
		assertThat( actual ).containsExactlyElementsIn( expected ).inOrder();
	}
	static Stream<Arguments> dataA() {
	    return 
	    Stream.of(
	    		Arguments.of( 
	    				List.of(), 
	    				'a',
	    				List.of() ),
	    		Arguments.of( 
	    				List.of("abba","ac/dc"), 
	    				'a',
	    				List.of("abba","ac/dc") ),
	    		Arguments.of( 
	    				List.of("Lorem","ipsum","dolor","sit","amet,","consectetur","adipiscing","elit"), 
	    				'a',
	    				List.of("amet,","adipiscing") ),
	    		Arguments.of( 
	    				List.of("sed do","eiusmod","tempor","incididunt","ut ","labore","et dolore","magna"," aliqua."), 
	    				' ',
	    				List.of("sed do","ut ","et dolore"," aliqua.") ),
	    		Arguments.of( 
	    				List.of("Velit","euismod","in","pellentesque","massa","placerat","duis","ultricies","lacus sed."), 
	    				'.',
	    				List.of("lacus sed.") ),
	    		Arguments.of( 
	    				List.of("Ut","consequat","semper","viverra nam","libero","justo","laoreet."),
	    				'u',
	    				List.of("consequat","justo") )
	    		);
	}
	@ParameterizedTest
	@MethodSource("dataB")
	void testB(List<Integer> input, List<Integer> expected) {
		UnaryOperator<Stream<Integer>> function = StreamFunctions.b;
		assertThat                   ( function ).isNotNull();

		var         stream = function.apply( input.stream() );
		assertThat( stream ).isNotNull();
		var         actual = stream.collect( Collectors.toList() );
		assertThat( actual ).containsExactlyElementsIn( expected ).inOrder();
	}
	static Stream<Arguments> dataB() {
	    return 
	    Stream.of(
	    		Arguments.of( 
	    				List.of(),
	    				List.of() ),
	    		Arguments.of( 
	    				List.of(-1 ),
	    				List.of( 0 ) ),
	    		Arguments.of( 
	    				List.of(-1, 0, 1 ),
	    				List.of( 0, 0, 0 ) ),
	    		Arguments.of( 
	    				List.of( 12, 43, Integer.MAX_VALUE,   Integer.MIN_VALUE,   2 ),
	    				List.of( 11, 42, Integer.MAX_VALUE-1, Integer.MIN_VALUE+1, 1 ) ),
	    		Arguments.of( 
	    				List.of(-2, -1, 0, 1, 2 ),
	    				List.of(-1,  0, 0, 0, 1 ) )
	    		);
	}
	@ParameterizedTest
	@MethodSource("dataC")
	void testC(List<Character> input, String expected) {
		Function<Stream<Character>,String> function = StreamFunctions.c;
		assertThat                       ( function ).isNotNull();

		var         actual = function.apply( input.stream() );
		assertThat( actual ).isNotNull();
		assertThat( actual ).isEqualTo( expected );
	} 
	static Stream<Arguments> dataC() {
	    return 
	    Stream.of(
	    		Arguments.of( 
	    				List.of(),
	    				"" ),
	    		Arguments.of( 
	    				List.of(' ',' ',' ',' ',' '),
	    				"" ),
	    		Arguments.of( 
	    				List.of('s',' ','k','y',' ',' ','w','a','l','k',' ',' ','e','r'),
	    				"skywalker" ),
	    		Arguments.of( 
	    				List.of('T','o','m',' ',' ','S','a','w','y','e','r',' '),
	    				"TomSawyer" ),
	    		Arguments.of( 
	    				List.of('F','o','o',' ','-',' ','F',' ','i',' ','g','h','t',' ','e','r'),
	    				"Foo-Fighter" )
	    		);
	}
	@ParameterizedTest
	@MethodSource("dataD")
	void testD(List<Integer[]> input, List<Integer> expected) {
		Function<Stream<Integer[]>,Stream<Integer>> function = StreamFunctions.d;
		assertThat                                ( function ).isNotNull();

		var         stream = function.apply( input.stream() );
		assertThat( stream ).isNotNull();
		var         actual = stream.collect( Collectors.toList() );
		assertThat( actual ).containsExactlyElementsIn( expected ).inOrder();
	}
	static Stream<Arguments> dataD() {
		Supplier<List<?>> supplier = () -> {
			List<Integer[]> one = new ArrayList<>();
			one.add(new Integer[]{6,1,5,2,4,3});
			return one;
		};
	    return 
	    Stream.of(
	    		Arguments.of( 
	    				List.of(),
	    				List.of() ),
	    		Arguments.of( 
	    				List.of(new Integer[]{1},new Integer[]{2},new Integer[]{3}),
	    				List.of(1,2,3) ),
	    		Arguments.of( 
	    				List.of(new Integer[]{3,2,1},new Integer[]{4},new Integer[]{5,6}),
	    				List.of(3,2,1,4,5,6) ),
	    		Arguments.of( 
	    				supplier.get(),
	    				List.of(6,1,5,2,4,3) ),
	    		Arguments.of( 
	    				List.of(new Integer[]{9},new Integer[]{},new Integer[]{},new Integer[]{42}),
	    				List.of(9,42) )
	    		);
	}
	@ParameterizedTest
	@MethodSource("dataE")
	void testE(IntStream input, Integer expected) {
		Function<IntStream,Integer> function = StreamFunctions.e;
		assertThat                ( function ).isNotNull();

		var         actual = function.apply( input );
		assertThat( actual ).isNotNull();
		assertThat( actual ).isEqualTo( expected );
	}
	static Stream<Arguments> dataE() {
	    return 
	    	Stream.of(
	    		Arguments.of( 
	    				IntStream.of(),
	    				0 ),
	    		Arguments.of( 
	    				IntStream.of(3),
	    				3 ),
	    		Arguments.of( 
	    				IntStream.of(9,2),
	    				11 ),
	    		Arguments.of( 
	    				IntStream.of(-1,0,45,-2),
	    				42 )
	    		);
	}
	
	private static final Person carly  = new Person( "Carly", 17, 10075, Person.Gender.FEMALE);
	private static final Person harry  = new Person( "Harry", 12, 23603, Person.Gender.MALE);
	private static final Person bob    = new Person( "Bob",   19, 23606, Person.Gender.MALE);
	private static final Person elsa   = new Person( "Elsa",   8, 23602, Person.Gender.FEMALE);
	private static final Person jon    = new Person( "Jon",   21, 90210, Person.Gender.MALE);
	private static final Person fred   = new Person( "Fred",  19, 23601, Person.Gender.MALE);
	private static final Person alice  = new Person( "Alice", 16, 23603, Person.Gender.FEMALE);
	private static final Person gale   = new Person( "Gale",  16, 23602, Person.Gender.FEMALE);
	private static final Person pedro  = new Person( "Pedro", 21, 42042, Person.Gender.MALE);
	private static final Person peter  = new Person( "Pedro", 42, 23601, Person.Gender.MALE);
	private static final Person alicia = new Person( "Alice", 25, 10075, Person.Gender.FEMALE);
	private static final Person bobby  = new Person( "Bob",   31, 23601, Person.Gender.MALE);
	
	private static final List<Person> EVERYBODY = List.of( carly, harry, bob, elsa, jon, fred, alice, gale, pedro, peter, alicia, bobby );

	@ParameterizedTest
	@MethodSource("dataF")
	void testF(List<Person> input, List<Person> expected) {
		UnaryOperator<Stream<Person>> function = StreamFunctions.f;
		assertThat                  ( function ).isNotNull();

		var         stream = function.apply( input.stream() );
		assertThat( stream ).isNotNull();
		var         actual = stream.collect( Collectors.toList() );
		assertThat( actual ).containsExactlyElementsIn( expected );
	}
	static Stream<Arguments> dataF() {
	    return 
	    	Stream.of(
	    		Arguments.of( 
	    				List.of(),
	    				List.of() ),
	    		Arguments.of( 
	    				List.of( alicia ),
	    				List.of() ),
	    		Arguments.of(
	    				List.of( carly, elsa, bobby, alice, gale ),
	    				List.of( bobby )),
	    		Arguments.of( 
	    				List.of( pedro, peter, harry, fred ),
	    				List.of( pedro, peter, fred )),
	    		Arguments.of( 
	    				EVERYBODY,
	    				List.of( bob, jon, pedro, bobby, peter, fred ))
	    );
	}
	@ParameterizedTest
	@MethodSource("dataG")
	void testG(List<Person> input, Long expected) {
		ToLongFunction<Stream<Person>> function = StreamFunctions.g;
		assertThat                   ( function ).isNotNull();

		var         actual = function.applyAsLong( input.stream() );
		assertThat( actual ).isNotNull();
		assertThat( actual ).isEqualTo( expected );
	}
	static Stream<Arguments> dataG() {
	    return 
	    	Stream.of(
	    		Arguments.of( 
	    				List.of(),
	    				0L ),
	    		Arguments.of( 
	    				List.of( elsa ),
	    				1L ),
	    		Arguments.of(
	    				List.of( alice, pedro, peter, alicia ),
	    				2L ),
	    		Arguments.of( 
	    				List.of( carly, harry, bob, elsa, jon, fred ),
	    				6L ),
	    		Arguments.of( 
	    				EVERYBODY,
	    				9L )
	    );
	}
	@ParameterizedTest
	@MethodSource("dataH")
	void testH(List<Person> input, List<String> expected) {
		Function<Stream<Person>,List<String>> function = StreamFunctions.h;
		assertThat                          ( function ).isNotNull();

		var         actual = function.apply( input.stream() );
		assertThat( actual ).isNotNull();
		assertThat( actual ).containsExactlyElementsIn( expected ).inOrder();
	}
	static Stream<Arguments> dataH() {
	    return 
	    	Stream.of(
	    		Arguments.of( 
	    				List.of(),
	    				List.of() ),
	    		Arguments.of( 
	    				List.of( jon ),
	    				List.of( jon.name() )),
	    		Arguments.of(
	    				List.of( alice, pedro, peter, alicia ),
	    				List.of( alice.name(), peter.name() )),
	    		Arguments.of( 
	    				List.of( carly, harry, bob, elsa, jon, fred ),
	    				List.of( bob.name(), carly.name(), elsa.name(), fred.name(), harry.name(), jon.name() )),
	    		Arguments.of( 
	    				EVERYBODY,
	    				List.of( alice.name(), bob.name(), carly.name(), elsa.name(), fred.name(), gale.name(), harry.name(), jon.name(), pedro.name() ))
	    );
	}
	@ParameterizedTest
	@MethodSource("dataI")
	void testH(List<Person> input, Map<Person.Gender,Person> expected) {
		Function<Stream<Person>,Map<Person.Gender,List<Person>>> function = StreamFunctions.i;
		assertThat                                             ( function ).isNotNull();

		var         actual = function.apply( input.stream() );
		assertThat( actual ).isNotNull();
		assertThat( actual ).containsExactlyEntriesIn( expected );
	}
	static Stream<Arguments> dataI() {
	    return 
	    	Stream.of(
	    		Arguments.of( 
	    				List.of(),
	    				Map .of() ),
	    		Arguments.of( 
	    				List.of( harry ),
	    				Map .of( Person.Gender.MALE, List.of( harry ))),
	    		Arguments.of(
	    				List.of( alicia, pedro ),
	    				Map .of( Person.Gender.MALE,   List.of( pedro ), 
	    						 Person.Gender.FEMALE, List.of( alicia ))),
	    		Arguments.of(
	    				List.of( carly, elsa, alice, gale ),
	    				Map .of( Person.Gender.FEMALE, List.of( carly, elsa, alice, gale ))),
	    		Arguments.of( 
	    				EVERYBODY,
	    				Map .of( Person.Gender.MALE,   List.of( harry, bob, jon, fred, pedro, peter, bobby ), 
	    						 Person.Gender.FEMALE, List.of( carly, elsa, alice, gale, alicia )))
	    );
	}
	@ParameterizedTest
	@MethodSource("dataJ")
	void testJ(List<Person> input, Map<Integer,List<Person>> expected) {
		Function<Stream<Person>,Map<Integer,List<String>>> function = StreamFunctions.j;
		assertThat                                       ( function ).isNotNull();

		var         actual = function.apply( input.stream() );
		assertThat( actual ).isNotNull();
		assertThat( actual ).containsExactlyEntriesIn( expected );
	}
	static Stream<Arguments> dataJ() {
	    return 
	    	Stream.of(
	    		Arguments.of( 
	    				List.of(),
	    				Map .of() ),
	    		Arguments.of( 
	    				List.of( harry ),
	    				Map .of( 23603, List.of( harry.name() ) )),
	    		Arguments.of(
	    				List.of( alicia, pedro ),
	    				Map .of( 10075, List.of( alicia.name() ),
	    				         42042, List.of( pedro .name() ) )),
	    		Arguments.of( 
	    				List.of( elsa, alice, gale ),
	    				Map .of( 23602, List.of( elsa .name(), gale.name() ),
	    						 23603, List.of( alice.name() ) )),
	    		Arguments.of( 
	    				EVERYBODY,
	    				Map .of( 10075, List.of( carly.name(), alicia.name() ),
	    						 23601, List.of( fred .name(), peter .name(), bobby.name() ),
	    						 23602, List.of( elsa .name(), gale  .name() ),
	    						 23603, List.of( harry.name(), alice .name() ),
	    						 23606, List.of( bob  .name() ),
	    						 42042, List.of( pedro.name() ),
	    						 90210, List.of( jon  .name() )
	    						))
	    );
	}
	@ParameterizedTest
	@MethodSource("dataK")
	void testK(List<Person> input, Map<Integer,Long> expected) {
		Function<Stream<Person>,Map<Integer,Long>> function = StreamFunctions.k;
		assertThat                               ( function ).isNotNull();

		var         actual = function.apply( input.stream() );
		assertThat( actual ).isNotNull();
		assertThat( actual ).containsExactlyEntriesIn( expected );
	}
	static Stream<Arguments> dataK() {
	    return 
	    	Stream.of(
	    		Arguments.of( 
	    				List.of(),
	    				Map .of() ),
	    		Arguments.of( 
	    				List.of( harry ),
	    				Map .of( 23603, 1L )),
	    		Arguments.of(
	    				List.of( alicia, pedro ),
	    				Map .of( 10075, 1L,
	    				         42042, 1L )),
	    		Arguments.of( 
	    				List.of( elsa, alice, gale ),
	    				Map .of( 23602, 2L,
	    						 23603, 1L )),
	    		Arguments.of( 
	    				EVERYBODY,
	    				Map .of( 10075, 2L,
	    						 23601, 3L,
	    						 23602, 2L,
	    						 23603, 2L,
	    						 23606, 1L,
	    						 42042, 1L,
	    						 90210, 1L
	    						))
	    );
	}
	@ParameterizedTest
	@MethodSource("dataL")
	void testL(List<Person> input, Map<Person.Gender,Optional<Person>> expected) {
		Function<Stream<Person>,Map<Person.Gender,Optional<Person>>> function = StreamFunctions.l;
		assertThat                                                 ( function ).isNotNull();

		var         actual = function.apply( input.stream() );
		assertThat( actual ).isNotNull();
		assertThat( actual ).containsExactlyEntriesIn( expected );
	}
	static Stream<Arguments> dataL() {
	    return 
	    	Stream.of(
	    		Arguments.of( 
	    				List.of(),
	    				Map .of() ),
	    		Arguments.of( 
	    				List.of( harry ),
	    				Map .of( Person.Gender.MALE, Optional.of( harry ) )),
	    		Arguments.of(
	    				List.of( alicia, pedro ),
	    				Map .of( Person.Gender.FEMALE, Optional.of( alicia ),
	    						 Person.Gender.MALE,   Optional.of( pedro  )) ),
	    		Arguments.of( 
	    				List.of( alicia, gale, carly ),
	    				Map .of( Person.Gender.FEMALE, Optional.of( gale )) ),
	    		Arguments.of( 
	    				EVERYBODY,
	    				Map .of( Person.Gender.FEMALE, Optional.of( elsa  ),
	    						 Person.Gender.MALE,   Optional.of( harry )) )
	    );
	}
	
	@ParameterizedTest
	@MethodSource("dataM")
	void testM(List<Person> input, Integer age, Map<Boolean,List<Person>> expected) {
		BiFunction<Stream<Person>,Integer,Map<Boolean,List<String>>> function = StreamFunctions.m;
		assertThat                                                 ( function ).isNotNull();

		var         actual = function.apply( input.stream(), age );
		assertThat( actual ).isNotNull();
		assertThat( actual ).containsExactlyEntriesIn( expected );
	}
	static Stream<Arguments> dataM() {
	    return 
	    	Stream.of(
	    		Arguments.of( 
	    				List.of(),
	    				22,
	    				Map .of( true,  List.of(),
	    						 false, List.of() )),
	    		Arguments.of( 
	    				List.of( jon ),
	    				22,
	    				Map .of( true,  List.of( jon.name() ),
	    						 false, List.of() )),
	    		Arguments.of(
	    				List.of( carly, harry, bob, elsa, jon, fred ),
	    				19,
	    				Map .of( true,  List.of( carly.name(), harry.name(), elsa.name() ),
	    						 false, List.of( bob  .name(), jon  .name(), fred.name() ) )),
	    		Arguments.of(
	    				List.of( carly, harry, bob, elsa, jon, fred ),
	    				20,
	    				Map .of( true,  List.of( carly.name(), harry.name(), bob.name(), elsa.name(), fred.name() ),
	    						 false, List.of( jon  .name() ) )),
	    		Arguments.of( 
	    				EVERYBODY,
	    				9,
	    				Map .of( true,  List.of( elsa .name() ),
	    						 false, List.of( carly.name(), harry.name(), bob.name(), jon.name(), fred.name(), alice.name(), gale.name(), pedro.name(), peter.name(), alicia.name(), bobby.name() ) )),
	    		Arguments.of( 
	    				EVERYBODY,
	    				21,
	    				Map .of( true,  List.of( carly.name(), harry.name(), bob  .name(), elsa  .name(), fred .name(), alice.name(), gale.name() ),
	    						 false, List.of( jon  .name(), pedro.name(), peter.name(), alicia.name(), bobby.name() ) ))
	    );
	}
}
