package io.split.client.impressions;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;

public class UniqueKeysTrackerImpTest {

    @Test
    public void addSomeElements(){
        UniqueKeysTrackerImp uniqueKeysTrackerImp = new UniqueKeysTrackerImp();
        Assert.assertTrue(uniqueKeysTrackerImp.track("feature1","key1"));
        Assert.assertTrue(uniqueKeysTrackerImp.track("feature1","key2"));
        Assert.assertTrue(uniqueKeysTrackerImp.track("feature1","key3"));
        Assert.assertTrue(uniqueKeysTrackerImp.track("feature2","key4"));
        Assert.assertTrue(uniqueKeysTrackerImp.track("feature2","key5"));

        HashMap<String, HashSet<String>> result = uniqueKeysTrackerImp.popAll();
        Assert.assertEquals(2,result.size());

        HashSet<String> value1 = result.get("feature1");
        Assert.assertEquals(3,value1.size());
        Assert.assertTrue(value1.contains("key1"));
        Assert.assertTrue(value1.contains("key2"));
        Assert.assertTrue(value1.contains("key3"));

        HashSet<String> value2 = result.get("feature2");
        Assert.assertEquals(2,value2.size());
        Assert.assertTrue(value2.contains("key4"));
        Assert.assertTrue(value2.contains("key5"));
    }

    @Test
    public void addTheSameElements(){
        UniqueKeysTrackerImp uniqueKeysTrackerImp = new UniqueKeysTrackerImp();
        Assert.assertTrue(uniqueKeysTrackerImp.track("feature1","key1"));
        Assert.assertTrue(uniqueKeysTrackerImp.track("feature1","key2"));
        Assert.assertTrue(uniqueKeysTrackerImp.track("feature1","key3"));

        Assert.assertFalse(uniqueKeysTrackerImp.track("feature1","key1"));
        Assert.assertFalse(uniqueKeysTrackerImp.track("feature1","key2"));
        Assert.assertFalse(uniqueKeysTrackerImp.track("feature1","key3"));

        HashMap<String, HashSet<String>> result = uniqueKeysTrackerImp.popAll();
        Assert.assertEquals(1,result.size());

        HashSet<String> value1 = result.get("feature1");
        Assert.assertEquals(3,value1.size());
        Assert.assertTrue(value1.contains("key1"));
        Assert.assertTrue(value1.contains("key2"));
        Assert.assertTrue(value1.contains("key3"));
    }

    @Test
    public void popAllMtks(){
        UniqueKeysTrackerImp uniqueKeysTrackerImp = new UniqueKeysTrackerImp();
        Assert.assertTrue(uniqueKeysTrackerImp.track("feature1","key1"));
        Assert.assertTrue(uniqueKeysTrackerImp.track("feature1","key2"));
        Assert.assertTrue(uniqueKeysTrackerImp.track("feature2","key3"));

        HashMap<String, HashSet<String>> result = uniqueKeysTrackerImp.popAll();
        Assert.assertEquals(2,result.size());
        HashMap<String, HashSet<String>> resultAfterPopAll = uniqueKeysTrackerImp.popAll();
        Assert.assertEquals(0,resultAfterPopAll.size());
    }
}