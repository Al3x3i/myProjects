using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace WindowsFormsApplication66
{
    class Drinks:Articles
    {
        public Drinks(int articleId, int type_id, string articleName, string articleType, Boolean alcoholic, decimal articlePrice, int article_stock)
            : base(articleId, type_id, articleName, articleType, alcoholic, articlePrice, article_stock)
        {

        }

        public override string ToString()
        {
            return "1." + base.ToString();
        }
        public override string GetID()
        {
            return "1." + base.GetID();
        }
    }
}
