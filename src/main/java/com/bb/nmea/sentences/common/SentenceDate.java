package com.bb.nmea.sentences.common;

import java.util.Calendar;
import java.util.Date;

public class SentenceDate {
	private final Integer m_dayOfMonth;
	private final Integer m_month;
	private final Integer m_year;
	

	public SentenceDate(final String strValue) {
		m_dayOfMonth = Integer.valueOf(strValue.substring(0,2));
		m_month = Integer.valueOf(strValue.substring(2,4));
		int twoDigitYear = Integer.valueOf(strValue.substring(4,6));
		if (twoDigitYear >= 70) {
			m_year = 1900 + twoDigitYear;
		} else {
			m_year = 2000 + twoDigitYear;
		}
	}


	/**
	 * @return the dayOfMonth
	 */
	public Integer getDayOfMonth() {
		return m_dayOfMonth;
	}


	/**
	 * @return the month
	 */
	public Integer getMonth() {
		return m_month;
	}


	/**
	 * @return the year
	 */
	public Integer getYear() {
		return m_year;
	}
	
	public Date getDate() {
		Calendar cal = Calendar.getInstance();
		
		cal.set(m_year, m_month, m_dayOfMonth, 0, 0, 0);
		
		return cal.getTime();
	}


	@Override
	public boolean equals(final Object obj) {
		// Not equals if other object is null
		if (obj == null)
			return false;
		
		// Not equals if other instance is no a SentenceDate
		if (! SentenceDate.class.isInstance(obj))
			return false;
		
		SentenceDate otherDate = SentenceDate.class.cast(obj);
		
		boolean dayOfMonthEquals = m_dayOfMonth == null ? otherDate.m_dayOfMonth == null 
				                                        : m_dayOfMonth.equals(otherDate.m_dayOfMonth);
		
		boolean monthEquals = m_month == null ? otherDate.m_month == null 
                                              : m_month.equals(otherDate.m_month);
		
		boolean yearEquals = m_year == null ? otherDate.m_year == null 
                                            : m_year.equals(otherDate.m_year);
		return dayOfMonthEquals && monthEquals && yearEquals;
	}


	@Override
	public int hashCode() {
		int result = 17;
		
		if (m_dayOfMonth != null)
			result = 31 * result + m_dayOfMonth.hashCode();
		
		if (m_month != null)
			result = 31 * result + m_month.hashCode();
		
		if (m_year != null)
			result = 31 * result + m_year.hashCode();
		
		return result;
	}
}
