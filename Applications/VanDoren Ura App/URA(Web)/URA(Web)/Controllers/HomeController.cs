using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Data.Entity.Validation;
using System.Data.SqlClient;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using URA_Web_.Models;

namespace URA_Web_.Controllers
{
    public class HomeController : Controller
    {
        //
        // GET: /Home/

        AccountContext db = new AccountContext();

        public ActionResult Index()
        {
            return View();
        }

        /// <summary>
        /// After submitting login form will be occured validation and if login/password are correct will be created Session which needs for rest controllers.
        /// </summary>
        /// <param name="accountFromForm">Account object</param>
        /// <returns></returns>
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Index(Account accountFromForm)
        {
            if (accountFromForm.Login != null && accountFromForm.Password != null)
            {
                string LoginKey = System.Configuration.ConfigurationManager.AppSettings["LoginName"];
                string LoginPassword = System.Configuration.ConfigurationManager.AppSettings["LoginPassword"];

               if (accountFromForm.Login.Equals(LoginKey) && accountFromForm.Password.Equals(LoginPassword))
               {
                    Session["LoginID"] = LoginKey;
                    Session["LoginPassword"] = LoginPassword;
                    ViewBag.Message = "";
                    return RedirectToAction("Overview");
               }
                // The entered login or password don't match to admistrartor login or passowrd, show message
               ViewBag.Message = "Wrong Login/Password";
                return View();
            }

            // Not all fields are filled in.
            return View();
        }

        /// <summary>
        /// Controller handles database to display AccountInfo data in table. The page has functionalities to create, edit and deactivate accounts
        /// </summary>
        /// <returns></returns>
        public ActionResult Overview()
        {
            string LoginKey = System.Configuration.ConfigurationManager.AppSettings["LoginName"];
            string LoginPassword = System.Configuration.ConfigurationManager.AppSettings["LoginPassword"];
            if (Session["LoginID"] != null && Session["LoginPassword"] != null &&
                Session["LoginID"].ToString() == LoginKey && Session["LoginPassword"].ToString() == LoginPassword)
            {
                List<Account> accountList = new List<Account>();
                foreach (Account item in db.Account.ToList())
                {
                    //Project requirement: not active user are not visible.
                    if (item.IsActive == true)
                    {
                        accountList.Add(item);
                    }
                }
                return View(accountList);
            }
            return RedirectToAction("Index");
        }

        /// <summary>
        /// Create new Account
        /// </summary>
        /// <returns></returns>
        [HttpGet]
        public ActionResult CreateAccount()
        {
            string LoginKey = System.Configuration.ConfigurationManager.AppSettings["LoginName"];
            string LoginPassword = System.Configuration.ConfigurationManager.AppSettings["LoginPassword"];

            if (Session["LoginID"] != null && Session["LoginPassword"] != null &&
               Session["LoginID"].ToString() == LoginKey && Session["LoginPassword"].ToString() == LoginPassword)
            {
                return View();
            }
            return RedirectToAction("Index");
        }

        /// <summary>
        /// After invoking this method will be created new account if login isn't duplicated and password's constraints (5 cars and 1 digit) are fulfilled.
        /// </summary>
        /// <param name="account"></param>
        /// <returns></returns>
        [HttpPost]
        public ActionResult CreateAccount(Account account)
        {
            string LoginKey = System.Configuration.ConfigurationManager.AppSettings["LoginName"];
            string LoginPassword = System.Configuration.ConfigurationManager.AppSettings["LoginPassword"];

            if (Session["LoginID"] != null && Session["LoginPassword"] != null &&
                Session["LoginID"].ToString() == LoginKey && Session["LoginPassword"].ToString() == LoginPassword)
            {
                if (ModelState.IsValid)
                    try
                    {
                        int keySize = Convert.ToInt32(System.Configuration.ConfigurationManager.AppSettings["RsaKeySize"]);
                        string rsaPublicKey = System.Configuration.ConfigurationManager.AppSettings["RsaPublicKey"];

                        account.ConfirmPassword = RSAEngine.EncryptText(account.ConfirmPassword, keySize, rsaPublicKey);
                        account.Password = account.ConfirmPassword;
                        account.SyntusFunction = account.SyntusFunction.Trim();
                        db.Account.Add(account);
                        account.Created = DateTime.Now;
                        db.SaveChanges();
                        ViewBag.Message = "Successfully added new account";
                    }

                    catch (System.Data.Entity.Infrastructure.DbUpdateException ex)
                    {
                        //"Dublicated" login constraint exception


                        SqlException s = ex.InnerException.InnerException as SqlException;

                        if (s.Number == 2601)
                        {
                            ViewData["LoginDublicateException"] = @"One of the properties is marked as Unique index and there is already an entry with that value:
                                                                    Login, URA_DBID, CAPA_DBID";
                            account.Login = null;
                            return View(account);
                        }
                    }

                    catch (Exception e)
                    {
                        return View(account);
                    }
                else
                {
                    return View(account);
                }
                return RedirectToAction("Overview");
            }
            return RedirectToAction("Index");
        }

        /// <summary>
        /// Contoller is used to display screen to change account properties
        /// </summary>
        /// <param name="id"></param>
        /// <returns></returns>
        public ActionResult Edit(Guid id)
        {
            string LoginKey = System.Configuration.ConfigurationManager.AppSettings["LoginName"];
            string LoginPassword = System.Configuration.ConfigurationManager.AppSettings["LoginPassword"];

            if (Session["LoginID"] != null && Session["LoginPassword"] != null &&
                Session["LoginID"].ToString() == LoginKey && Session["LoginPassword"].ToString() == LoginPassword)
            {
                Account account = db.Account.Find(id);

                if (account == null)
                {
                    return HttpNotFound();
                }
                return View(account);
            }
            return RedirectToAction("Index");
        }

        /// <summary>
        /// Controller allows to change only account password.
        /// </summary>
        /// <param name="account"></param>
        /// <returns></returns>
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Edit(Account account)
        {
            string LoginKey = System.Configuration.ConfigurationManager.AppSettings["LoginName"];
            string LoginPassword = System.Configuration.ConfigurationManager.AppSettings["LoginPassword"];

            if (Session["LoginID"] != null && Session["LoginPassword"] != null &&
                Session["LoginID"].ToString() == LoginKey && Session["LoginPassword"].ToString() == LoginPassword)
            {

                bool pwValidate = false;
                if (Request.Form["check"] != null)
                {
                    pwValidate = true;
                }

                if (ModelState.IsValid || !pwValidate)
                {
                    try
                    {
                        int keySize = Convert.ToInt32(System.Configuration.ConfigurationManager.AppSettings["RsaKeySize"]);
                        string rsaPublicKey = System.Configuration.ConfigurationManager.AppSettings["RsaPublicKey"];

                        Account temp = db.Account.Find(account.AccountID);

                        if (pwValidate)
                        {
                            temp.Password = RSAEngine.EncryptText(account.Password, keySize, rsaPublicKey);
                            temp.ConfirmPassword = temp.Password;
                        }
                        else
                        {
                            temp.ConfirmPassword = temp.Password;
                        }

                        temp.URA_DBID = account.URA_DBID;
                        temp.SyntusFunction = account.SyntusFunction;
                        temp.Capa_DBID = account.Capa_DBID;
                        db.Entry(temp).State = EntityState.Modified;
                        db.SaveChanges();
                        return RedirectToAction("Overview");
                    }
                    catch (Exception ex)
                    {
                        return RedirectToAction("Action");
                    }
                }
                return View(account);
            }
            return RedirectToAction("Action");
        }

        /// <summary>
        /// This controller changes state of account. If field "IsActive" is false then it won't be displayed in table and not available to use by mobile app
        /// </summary>
        /// <param name="id"></param>
        /// <returns></returns>
        public ActionResult DeactivateAccount(Guid id)
        {
            string LoginKey = System.Configuration.ConfigurationManager.AppSettings["LoginName"];
            string LoginPassword = System.Configuration.ConfigurationManager.AppSettings["LoginPassword"];

            if (Session["LoginID"] != null && Session["LoginPassword"] != null &&
                Session["LoginID"].ToString() == LoginKey && Session["LoginPassword"].ToString() == LoginPassword)
            {
                Account account = db.Account.Find(id);
                account.IsActive = false;
                account.ConfirmPassword = account.Password;

                db.Entry(account).State = EntityState.Modified;
                db.SaveChanges();
                return RedirectToAction("Overview");
            }
            return RedirectToAction("Index");
        }

        /// <summary>
        /// Close database connection
        /// </summary>
        /// <param name="dissposing"></param>
        protected override void Dispose(bool dissposing)
        {
            db.Dispose();
            base.Dispose(dissposing);
        }
    }
}
