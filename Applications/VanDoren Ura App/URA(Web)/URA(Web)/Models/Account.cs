using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace URA_Web_.Models
{
    [Table("AccountInfo")]
    public class Account
    {
        public Account()
        {
            IsActive = true;
            IsOnline = false;
        }
        [Key]
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)] // generates automatecally new Guid value
        public Guid AccountID { get; set; }

        [Column(TypeName = "VARCHAR")]
        [StringLength(20)]
        [Required(ErrorMessage = "Please provide First Name", AllowEmptyStrings = false)]
        public string FirstName { get; set; }

        [Column(TypeName = "VARCHAR")]
        [StringLength(20)]
        public string MiddleName { get; set; }

        [Column(TypeName = "VARCHAR")]
        [StringLength(20)]
        [Required(ErrorMessage = "Please provide Second Name", AllowEmptyStrings = false)]
        public string SecondName { get; set; }

        [Column(TypeName = "VARCHAR"), Index(IsUnique = true)]
        [StringLength(15)]
        [Required(ErrorMessage = "Please provide Login", AllowEmptyStrings = false)]
        public string Login { get; set; }

        [Column(TypeName = "VARCHAR")]
        [StringLength(500)]
        [Required(ErrorMessage = "Please provide Password", AllowEmptyStrings = false)]
        [RegularExpression("(?=.*[0-9])(?=.*[a-zA-Z0-9]{4}).*", ErrorMessage = "Password should have minimum 5 characters and at least 1 digit")]
        [DataType(System.ComponentModel.DataAnnotations.DataType.Password)]
        public string Password { get; set; }

        [NotMapped]
        [StringLength(500)]
        [Compare("Password", ErrorMessage = "Confirm password not match")]
        [DataType(System.ComponentModel.DataAnnotations.DataType.Password)]
        public string ConfirmPassword { get; set; }

        [Column(TypeName = "bigint"), Index(IsUnique = false)]
        public Nullable<int> URA_DBID { get; set; }

        [Column(TypeName = "VARCHAR")]
        [StringLength(10)]
        public string SyntusFunction { get; set; }

        [Column(TypeName = "uniqueidentifier"), Index(IsUnique = false)]
        public Nullable<Guid> Capa_DBID { get; set; }

        public bool IsActive { get; set; }

        public bool IsOnline { get; set; }

        [DataType(DataType.Date)]
        [DisplayFormat(DataFormatString = "{0:yyyy-MM-dd}", ApplyFormatInEditMode = true)]
        public DateTime Created { set; get; }

        public ICollection<LoginHistory> LoginHistoryID { get; set; }
    }

    [Table("LoginHistory")]
    public class LoginHistory
    {
        [Key]
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public Guid ID { get; set; }

        [Required]
        [DisplayFormat(DataFormatString = "{0:yyyy-MM-dd}", ApplyFormatInEditMode = true)]
        public DateTime LoggedIn { get; set; }

        public DateTime? LoggedOut { get; set; }

        [Column(TypeName = "VARCHAR")]
        [Required]
        [StringLength(20)]
        public String PhoneImei { get; set; }

        [ForeignKey("Account")]
        public Guid AccountID { get; set; }

        [NotMapped]
        public string LoginName { get; set; }

        [Required]
        public virtual Account Account { get; set; } // TO DO Can be exception -> Find The way how to prevent exception when I want block user. it requires same Account

    }

    [Table("BlockedPhoneImei")]
    public class BlockedPhoneImei
    {
        [Key]
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public Guid ID { get; set; }

        [DataType(DataType.Date)]
        [DisplayFormat(DataFormatString = "{0:yyyy-MM-dd}", ApplyFormatInEditMode = true)]
        public DateTime BlockedDate { get; set; }

        [Column(TypeName = "VARCHAR")]
        [Required]
        [StringLength(20)]
        public String PhoneImei { get; set; }

        [NotMapped]
        public string LoginName { get; set; }

        [ForeignKey("Account")]
        public Guid AccountID { get; set; }

        [Required]
        public virtual Account Account { get; set; }

    }
}