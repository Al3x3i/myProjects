using System;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using WindowsFormsApplication66;

namespace ShopUnitTest
{
    [TestClass]
    public class UnitTest1
    {
        [TestMethod]
        public void TestCreateArticles()
        {
            Articles a;
            a = new Articles(13, 13,"Bavaria", "Draken", true, (decimal)1.85, 100);

            Assert.AreEqual(a.GetArticleId, 13);
            Assert.AreEqual(a.GetTypeID, 13);
            Assert.AreEqual(a.GetName, "Bavaria");
            Assert.AreEqual(a.GetTypeName, "Draken");
            Assert.AreEqual(a.Alcoholic, true);
            Assert.AreEqual(a.GetArticlePrice, 1.85f);
            Assert.AreEqual(a.GetStock, 100);

            Articles b = new Articles(0, 0, "Thee", "Draken", false, (decimal)1.6, 100);
            Assert.AreEqual(b.GetArticleId, 0);
            Assert.AreEqual(b.GetTypeID, 0);
            Assert.AreEqual(b.GetName, "Thee");
            Assert.AreEqual(b.GetTypeName, "Draken");
            Assert.AreEqual(b.Alcoholic, false);
            Assert.AreEqual(b.GetArticlePrice, 1.6f);
            Assert.AreEqual(b.GetStock, 100);

            Articles c = new Articles(27, 3, "Ballon", "Stuff", false, (decimal)1, 50);
            Assert.AreEqual(c.GetArticleId, 27);
            Assert.AreEqual(c.GetTypeID, 3);
            Assert.AreEqual(c.GetName, "Ballon");
            Assert.AreEqual(c.GetTypeName, "Stuff");
            Assert.AreEqual(c.Alcoholic, false);
            Assert.AreEqual(c.GetArticlePrice, 1f);
            Assert.AreEqual(c.GetStock, 50);
        }

        
    }
}
