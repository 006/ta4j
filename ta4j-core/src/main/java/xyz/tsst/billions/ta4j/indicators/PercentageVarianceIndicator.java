/**
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2007-2023 Ttron Kidman. All rights reserved.
 */
package xyz.tsst.billions.ta4j.indicators;

import org.ta4j.core.Indicator;
import org.ta4j.core.indicators.CachedIndicator;
import org.ta4j.core.indicators.SMAIndicator;
import org.ta4j.core.num.Num;

/**
 * @Ttron Dec 26, 2023
 */
public class PercentageVarianceIndicator extends CachedIndicator<Num>
{
	private final Indicator<Num> indicator;

	private final int barCount;

	private final SMAIndicator sma;

	/**
	 * @param series
	 */
	public PercentageVarianceIndicator(Indicator<Num> indicator, int barCount)
	{
		super( indicator );
		this.indicator = indicator;
		this.barCount = barCount;
		this.sma = new SMAIndicator( indicator, barCount );
	}


	@Override
	public int getUnstableBars()
	{
		return barCount;
	}


	@Override
	protected Num calculate(int index)
	{
		final int startIndex = Math.max( 0, index - barCount + 1 );
		final int numberOfObservations = index - startIndex + 1;
		Num variance = zero();
		Num average = sma.getValue( index );
		for ( int i = startIndex; i <= index; i++ )
		{
			Num pow = indicator.getValue( i ).minus( average ).dividedBy( average ).pow( 2 );
			variance = variance.plus( pow );
		}
		variance = variance.dividedBy( numOf( numberOfObservations ) );
		return variance;
	}


	public Num[] getValues(int index)
	{
		Num[] array = new Num[2];
		final int startIndex = Math.max( 0, index - barCount + 1 );
		final int numberOfObservations = index - startIndex + 1;
		Num variance = zero();
		Num average = sma.getValue( index );
		Num valueLast = indicator.getValue( index );
		for ( int i = startIndex; i <= index; i++ )
		{
			Num pow = indicator.getValue( i ).minus( average ).dividedBy( average ).pow( 2 );
			variance = variance.plus( pow );
		}
		variance = variance.dividedBy( numOf( numberOfObservations ) );
		array[0] = variance;
		array[1] = valueLast.minus( average ).dividedBy( average );
		return array;
	}
}
