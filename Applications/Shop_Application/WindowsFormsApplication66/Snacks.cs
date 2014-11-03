using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace WindowsFormsApplication66
{
    class Snacks:Articles
    {
        public Snacks(int articleId, int type_id, string articleName, string articleType, Boolean alcoholic, decimal articlePrice, int article_stock)
            : base(articleId, type_id, articleName, articleType, false, articlePrice, article_stock)
        {

        }
        public override string ToString()
        {
            return "2." + base.ToString();
        }
        public override string GetID()
        {
            return "2." + base.GetID();
        }
    }
}
