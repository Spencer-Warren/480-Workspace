package Stream;
import static com.google.common.truth.Truth.assertThat;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import Stream.StreamCreation;

class StreamCreationTest {
	@Test
	void testGenerate() {
		DoubleStream stream = StreamCreation.generate;
		assertThat ( stream ).isNotNull();
		// get numbers
		var count  = 100_000;
		var actual = stream.limit( count ).mapToObj( Double::valueOf ).sorted().collect( Collectors.toList() );
		// 0.5 < numbers < 0.8
		actual.stream().forEach( n -> {
				assertThat( n ).isGreaterThan( 0.5 );	
				assertThat( n ).isLessThan   ( 0.8 );
		});
		// numbers are infinite
		assertThat( actual ).hasSize( count );
		// numbers are different
		var first = actual.get( 0 );
		var last  = actual.get( count-1 );
		assertThat( first ).isLessThan( last );
	}

	@Test
	void testIterate() {
		Stream<Character> stream = StreamCreation.iterate;
		assertThat      ( stream ).isNotNull();
		
		var expected = List.of('a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z');
		var actual   = stream.limit( Integer.MAX_VALUE ).collect( Collectors.toList() );
		assertThat( actual ).containsExactlyElementsIn( expected ).inOrder();
	}

	@Test
	void testOf() {
		Stream<Boolean> stream = StreamCreation.of;
		assertThat    ( stream ).isNotNull();
		
		var expected = List.of( true, false, true );
		var actual   = StreamCreation.of.collect( Collectors.toList() );
		assertThat( actual ).containsExactlyElementsIn( expected ).inOrder();
	}
	
	@Test
	void testBuilder() {
		DoubleStream.Builder stream = StreamCreation.builder;
		assertThat         ( stream ).isNotNull();
		
		var expected = List.of( 3.1416, -42.0, 12.34 );
		var actual   = StreamCreation.builder.build().boxed().collect( Collectors.toList() );
		assertThat( actual ).containsExactlyElementsIn( expected ).inOrder();
	}
	
	@ParameterizedTest
	@MethodSource("dataRange")
	void testRange(int begin, int end, List<Integer> expected) {
		BiFunction<Integer,Integer,IntStream> function = StreamCreation.range;
		assertThat                          ( function ).isNotNull();
		
		var         stream = StreamCreation.range.apply( begin, end );
		assertThat( stream ).isNotNull();
		var         actual = stream.boxed().collect( Collectors.toList() );
		assertThat( actual ).containsExactlyElementsIn( expected ).inOrder();
	}
	static Stream<Arguments> dataRange() {
	    return Stream.of(
	        Arguments.of(  0,  0, List.of()),
	        Arguments.of(  1,  2, List.of(1)),
	        Arguments.of(  1,  5, List.of(1,2,3,4)),
	        Arguments.of( -2,  3, List.of(-2,-1,0,1,2)),
	        Arguments.of( 11, 23, List.of(11,12,13,14,15,16,17,18,19,20,21,22)),
	        Arguments.of(  1,  0, List.of())
	    );
	}

	@ParameterizedTest
	@MethodSource("stream")
	void testStream(long[] input, List<Long> expected) {
		Function<long[],LongStream> function = StreamCreation.stream;
		assertThat                ( function ).isNotNull();
		
		var         stream = function.apply( input );
		assertThat( stream ).isNotNull();
		var         actual = stream.boxed().collect( Collectors.toList() );
		assertThat( actual ).containsExactlyElementsIn( expected ).inOrder();
	}
	static Stream<Arguments> stream() {
	    return Stream.of(
	        Arguments.of( new long[] {},
                          List.of() ),
	        Arguments.of( new long[] {1,2},
                          List.of() ),
	        Arguments.of( new long[] {3,4,5,6},
                          List.of(4L,5L) ),
	        Arguments.of( new long[] {-1,0,7,11,0,42},
                          List.of(0L,7L,11L,0L) )
	    );
	}

	@ParameterizedTest
	@MethodSource("concat")
	void testConcat(Stream<String> one, Stream<String> two, List<String> expected) {
		BinaryOperator<Stream<String>> operator = StreamCreation.concat;
		assertThat                   ( operator ).isNotNull();

		var         stream = operator.apply( one, two );
		assertThat( stream ).isNotNull();;
		var         actual = stream.collect( Collectors.toList() );
		assertThat( actual ).containsExactlyElementsIn( expected ).inOrder();
	}
	static Stream<Arguments> concat() {
	    return Stream.of(
	        Arguments.of( Stream.empty(),
                          Stream.empty(),
                          List.of() ),
	        Arguments.of( Stream.empty(),
                          List.of("ab","c","de").stream(),
                          List.of("ab","c","de")),
	        Arguments.of( List.of("1","23","456","7").stream(), 
                          Stream.empty(), 
                          List.of("1","23","456","7")),
	        Arguments.of( List.of("a","bb","a").stream(), 
                          List.of("ac","/","dc").stream(), 
                          List.of("a","bb","a","ac","/","dc")),
	        Arguments.of( List.of("1","2","3","4","5").stream(), 
                          List.of("6","7").stream(), 
                          List.of("1","2","3","4","5","6","7"))
	    );
	}
}
