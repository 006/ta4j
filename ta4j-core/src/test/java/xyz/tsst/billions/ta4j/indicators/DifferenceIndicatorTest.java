package xyz.tsst.billions.ta4j.indicators;

import static junit.framework.TestCase.assertEquals;
import static org.ta4j.core.TestUtils.assertNumEquals;

import java.util.function.Function;

import org.junit.Before;
import org.junit.Test;
import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.indicators.AbstractIndicatorTest;
import org.ta4j.core.indicators.helpers.VolumeIndicator;
import org.ta4j.core.mocks.MockBarSeries;
import org.ta4j.core.num.Num;

public class DifferenceIndicatorTest extends AbstractIndicatorTest<Indicator<Num>, Num>
{
	private DifferenceIndicator diff;

	private BarSeries barSeries;

	public DifferenceIndicatorTest(Function<Number, Num> numFunction)
	{
		super( numFunction );
	}


	@Before
	public void setUp()
	{
		barSeries = new MockBarSeries( numFunction );
		// System.out.println( barSeries.getFirstBar() );
		VolumeIndicator volumeIndicator = new VolumeIndicator( barSeries );
		diff = new DifferenceIndicator( volumeIndicator );
	}


	@Test
	public void indicatorShouldRetrieveBarDifference()
	{
		System.out.println( "----------------------------------------" );
		assertNumEquals( 0, diff.getValue( 0 ) );
		for ( int i = 1; i < 10; i++ )
		{
			Num previousBarClosePrice = barSeries.getBar( i - 1 ).getVolume();
			Num currentBarClosePrice = barSeries.getBar( i ).getVolume();
			System.out.println( diff.getValue( i ) + ", " + previousBarClosePrice + "/" + currentBarClosePrice );
			assertEquals( diff.getValue( i ), currentBarClosePrice.minus( previousBarClosePrice ) );
		}
	}
}
