/*! ******************************************************************************
 *
 * Pentaho
 *
 * Copyright (C) 2024 by Hitachi Vantara, LLC : http://www.pentaho.com
 *
 * Use of this software is governed by the Business Source License included
 * in the LICENSE.TXT file.
 *
 * Change Date: 2029-07-20
 ******************************************************************************/


package org.pentaho.metaverse.analyzer.kettle.extensionpoints;

import org.pentaho.di.core.bowl.Bowl;
import org.pentaho.di.job.entry.JobEntryBase;
import org.pentaho.metaverse.api.IAnalysisContext;
import org.pentaho.metaverse.api.analyzer.kettle.jobentry.IJobEntryExternalResourceConsumer;
import org.pentaho.metaverse.api.model.IExternalResourceInfo;

import java.util.Collection;

/**
 * Helper class to provide an implementation of IJobEntryExternalResourceConsumer for testing
 */
public class JobEntryExternalResourceConsumerStub implements IJobEntryExternalResourceConsumer {

  @Override
  public boolean isDataDriven( Object meta ) {
    return false;
  }

  @Override
  public Collection<IExternalResourceInfo> getResourcesFromMeta( Bowl bowl, Object meta ) {
    return null;
  }

  @Override
  public Collection<IExternalResourceInfo> getResourcesFromMeta( Bowl bowl, Object consumer, IAnalysisContext context ) {
    return null;
  }

  @Override
  public Class<?> getMetaClass() {
    return JobEntryBase.class;
  }
}
