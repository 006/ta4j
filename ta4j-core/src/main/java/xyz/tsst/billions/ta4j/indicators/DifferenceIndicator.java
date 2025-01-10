/**
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2007-2024 Ttron Kidman. All rights reserved.
 */
package xyz.tsst.billions.ta4j.indicators;

import org.ta4j.core.Indicator;
import org.ta4j.core.indicators.AbstractIndicator;
import org.ta4j.core.num.Num;

/**
 * @MR006 Nov 1, 2024
 */
public class DifferenceIndicator extends AbstractIndicator<Num>
{
	private Indicator<Num> indicator;

	/**
	 * @param series
	 */
	public DifferenceIndicator(Indicator<Num> indicator)
	{
		super( indicator.getBarSeries() );
		this.indicator = indicator;
	}


	@Override
	public int getUnstableBars()
	{
		return 0;
	}


	@Override
	public Num getValue(int index)
	{
		Num previousValue = indicator.getValue( Math.max( 0, index - 1 ) );
		Num currentValue = indicator.getValue( index );
		// Calculate the difference between the current and previous values
		return currentValue.minus( previousValue );
	}
}
