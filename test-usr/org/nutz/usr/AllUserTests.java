package org.nutz.usr;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.nutz.usr.fs.FsUserApiTest;
import org.nutz.usr.mongo.MongoUserApiTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({FsUserApiTest.class, MongoUserApiTest.class})
public class AllUserTests {}
