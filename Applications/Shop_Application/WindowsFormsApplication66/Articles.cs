using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace WindowsFormsApplication66
{
    public class Articles
    {
        int articleId;
        int type_id;
        string articleName;
        string articleType;
        Boolean alcoholic;
        decimal articlePrice;
        int article_stock;

        public Articles(int articleId, int type_id, string articleName, string articleType, Boolean alcoholic, decimal articlePrice, int article_stock)
        {
            this.articleId = articleId;
            this.type_id = type_id;
            this.articleName = articleName;
            this.articleType = articleType;
            this.alcoholic = alcoholic;
            this.articlePrice = articlePrice;
            this.article_stock = article_stock;
        }

        public int GetArticleId { get { return this.articleId; } }

        public decimal GetArticlePrice { get { return this.articlePrice; } }

        public int GetTypeID { get { return this.type_id; } }
        public String GetName { get { return this.articleName; } }
        public String GetTypeName { get { return this.articleType; } }
        public bool Alcoholic { get { return this.alcoholic; } }
        public int GetStock { get { return this.article_stock; } }

        public virtual string ToString()
        {
            return type_id+" " + articleName + " " + articlePrice.ToString() + " EURO";
        }

        public virtual string GetID()
        {
            return type_id.ToString();
        }
    }
}
