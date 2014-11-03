using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace WindowsFormsApplication66
{
    class Stuff:Articles
    {
        public Stuff(int articleId, int type_id, string articleName, string articleType, Boolean alcoholic, decimal articlePrice, int article_stock)
            : base(articleId,type_id, articleName, articleType, false, articlePrice, article_stock)
        {

        }

        public override string ToString()
        {
            return "3." + base.ToString();
        }
        public override string GetID()
        {
            return "3." + base.GetID();
        }

    }
}
/// Every time when programm starts stuff data should be updated.
/// After money transfer I Order class should be restarted
/// 
/// What I should to do:::   TO DO
/// Handle try catch,
/// solve problem with float number,
/// handle orderList ( if client decided not to use by cola, use button remove) If order list is emty, do something >>
/// All BUTTONS
/// IF how to do: only special class hadle all classes other may not
