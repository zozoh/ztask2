package org.nutz.dmn;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.nutz.dmn.mongo.MongoDomainApiTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({MongoDomainApiTest.class})
public class AllDomainTest {}
