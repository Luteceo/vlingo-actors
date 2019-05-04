// Copyright © 2012-2018 Vaughn Vernon. All rights reserved.
//
// This Source Code Form is subject to the terms of the
// Mozilla Public License, v. 2.0. If a copy of the MPL
// was not distributed with this file, You can obtain
// one at https://mozilla.org/MPL/2.0/.

package io.vlingo.actors;

import io.vlingo.common.Completes;

public class PooledCompletes implements CompletesEventually {
  public final Completes<Object> clientCompletes;
  public final CompletesEventually completesEventually;
  public final long id;
  private Object outcome;

  @SuppressWarnings("unchecked")
  public PooledCompletes(
          final long id,
          final Completes<?> clientCompletes,
          final CompletesEventually completesEventually) {
    this.id = id;
    this.clientCompletes = (Completes<Object>) clientCompletes;
    this.completesEventually = completesEventually;
  }

  @Override
  public Address address() {
    return completesEventually.address();
  }

  public Object outcome() {
    return outcome;
  }

  @Override
  public void with(final Object outcome) {
    this.outcome = outcome;
    completesEventually.with(this);
  }

  @Override
  public void conclude() { }

  @Override
  public boolean isStopped() {
    return completesEventually.isStopped();
  }

  @Override
  public void stop() { }

  @Override
  public int hashCode() {
    return 31 * address().hashCode();
  }

  @Override
  public boolean equals(final Object other) {
    if (other == null || other.getClass() != getClass()) {
      return false;
    }
    return address().equals(((PooledCompletes)other).address());
  }

  @Override
  public String toString() {
    return "PooledCompletes[id=" + id + " address=" + address() + "]";
  }
}
