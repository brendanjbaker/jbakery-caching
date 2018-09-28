package org.jbakery.caching;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import org.jbakery.arguments.Argument;

public class AbsoluteExpirationCache<T>
	implements Cache<T>
{
	private final Clock clock;
	private final Duration duration;

	private volatile Instant expiration;
	private volatile T item;

	public AbsoluteExpirationCache(Clock clock, Duration duration)
	{
		this.clock = Argument.notNull(clock, "clock");
		this.duration = Argument.notNull(duration, "duration");
	}

	@Override
	public void set(T item)
	{
		this.item = item;
		this.expiration = clock.instant().plus(duration);
	}

	@Override
	public T tryGet()
	{
		if (expiration == null)
			return null;

		if (clock.instant().isAfter(expiration))
			return null;

		return item;
	}
}
