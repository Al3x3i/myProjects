using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Data.Entity;
using System.Data.Entity.Infrastructure;

namespace URA_Web_.Models
{
    public class AccountContext : DbContext
    {
        public AccountContext()
            : base("name=VanDorenURAConnection")
        {

        }

        protected override void OnModelCreating(DbModelBuilder modelBuilder)
        {
            base.OnModelCreating(modelBuilder);
        }

        public virtual DbSet<Account> Account { get; set; }
        public virtual DbSet<LoginHistory> LoginHistory { get; set; }
        public virtual DbSet<BlockedPhoneImei> BlockedPhoneImei { get; set; }
    }
}