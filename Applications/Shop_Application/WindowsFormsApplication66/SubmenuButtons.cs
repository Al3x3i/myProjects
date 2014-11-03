using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Windows.Forms;


namespace WindowsFormsApplication66
{

    class SubmenuButtons
    {
        List<string> dranken;
        List<string> snacks;
        List<string> stuff;

        Button[] DrankenButtons;
        Button[] SnaksButtons;
        Button[] StuffButtons;
        Button[] currentSubmenyButtons; //buttons which at this moment displayed on group box
        object ButtonColor =null;
        GroupBox gbSubMenu;
        int subMenuDisplayedButtons = 0;

        public SubmenuButtons(GroupBox gb, EventHandler buttonClick, List<Articles>articles)
        {

            dranken = new List<string>();
            snacks = new List<string>();
            stuff = new List<string>();

            foreach (var item in articles)
            {
                if (item is Drinks)
                {
                    dranken.Add(item.GetName);
                }
                else if (item is Snacks)
                {
                    snacks.Add(item.GetName);
                }
                else
                {
                    stuff.Add(item.GetName); 
                }
            }

            DrankenButtons = new Button[dranken.Count];
            SnaksButtons = new Button[snacks.Count];
            StuffButtons = new Button[stuff.Count];

            gbSubMenu = gb;
            CreateDrunkenButtons(buttonClick);
            CreateSnaksButtons(buttonClick);
            CreateStuffButtons(buttonClick);
        }
        public Button[] getButtons(string type)
        {
            if (type == "dranken"){ return DrankenButtons; }
            else if (type == "snacks"){ return SnaksButtons; }
            else{ return StuffButtons; }
        }
        public int SubMenuDisplayedButtons
        {
            get { return subMenuDisplayedButtons; }
            set { subMenuDisplayedButtons = value; }
        }
        public void CreateDrunkenButtons(EventHandler buttonClick)
        {
            int widthOfAButton = 135;
            int spaceBetweenButtons = 10;
            int x = 10;
            int y = 25;
            int counter = 0;
            for (int i = 0; i < DrankenButtons.Length; i++)
            {
                DrankenButtons[i] = new Button();
                DrankenButtons[i].Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
                DrankenButtons[i].ForeColor = System.Drawing.Color.Black;
                //DrunkenButtons[i].Image = SubMenuImages.Images[i];
                //DrunkenButtons[i].ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
                DrankenButtons[i].Hide();
                DrankenButtons[i].Location = new System.Drawing.Point(x, y);
                DrankenButtons[i].Size = new System.Drawing.Size(135, 50);
                DrankenButtons[i].UseVisualStyleBackColor = true;
                gbSubMenu.Controls.Add(DrankenButtons[i]);
                DrankenButtons[i].Text = dranken[i];
                DrankenButtons[i].Tag = "1." +i;
                DrankenButtons[i].Click += buttonClick;
                x = x + widthOfAButton + spaceBetweenButtons;
                counter++;
                if (counter % 3 == 0)
                {
                    y += 60;
                    x = 10;
                    if (counter == 9 )
                        {
                            counter = 0;
                            y = 25;
                        }
                }
            }
        }
        public void CreateSnaksButtons(EventHandler buttonClick)
        {
            int widthOfAButton = 135;
            int spaceBetweenButtons = 10;
            int x = 10;
            int y = 25;
            int counter = 0;
            for (int i = 0; i < SnaksButtons.Length; i++)
            {
                SnaksButtons[i] = new Button();
                SnaksButtons[i].Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
                SnaksButtons[i].ForeColor = System.Drawing.Color.Black;
                //DrunkenButtons[i].Image = SubMenuImages.Images[i];
                //DrunkenButtons[i].ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
                SnaksButtons[i].Hide();
                SnaksButtons[i].Location = new System.Drawing.Point(x, y);
                SnaksButtons[i].Size = new System.Drawing.Size(135, 50);
                SnaksButtons[i].UseVisualStyleBackColor = true;
                gbSubMenu.Controls.Add(SnaksButtons[i]);
                SnaksButtons[i].Text = snacks[i];
                SnaksButtons[i].Tag = "2." + i;
                SnaksButtons[i].Click += buttonClick;
                x = x + widthOfAButton + spaceBetweenButtons;
                counter++;
                if (counter % 3 == 0)
                {
                    y += 60;
                    x = 10;
                    if (counter == 9)
                    {
                        counter = 0;
                        y = 25;
                    }
                }
            }
        }
        public void CreateStuffButtons(EventHandler buttonClick)
        {
            int widthOfAButton = 135;
            int spaceBetweenButtons = 10;
            int x = 10;
            int y = 25;
            int counter = 0;
            for (int i = 0; i < StuffButtons.Length; i++)
            {
                StuffButtons[i] = new Button();
                StuffButtons[i].Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
                StuffButtons[i].ForeColor = System.Drawing.Color.Black;
                //DrunkenButtons[i].Image = SubMenuImages.Images[i];
                //DrunkenButtons[i].ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
                StuffButtons[i].Hide();
                StuffButtons[i].Location = new System.Drawing.Point(x, y);
                StuffButtons[i].Size = new System.Drawing.Size(135, 50);
                StuffButtons[i].UseVisualStyleBackColor = true;
                gbSubMenu.Controls.Add(StuffButtons[i]);
                StuffButtons[i].Text = stuff[i];
                StuffButtons[i].Tag = "3." + i;
                StuffButtons[i].Click += buttonClick;
                x = x + widthOfAButton + spaceBetweenButtons;
                counter++;
                if (counter % 3 == 0)
                {
                    y += 60;
                    x = 10;
                    if (counter == 9)
                    {
                        counter = 0;
                        y = 25;
                    }
                }
            }
        }
        public void ShowButtons(string buttonType)
        {
            if (Equals(currentSubmenyButtons, getButtons(buttonType)) == false)
            {
                if (currentSubmenyButtons != null)
                {
                    int counter = 0;
                    while (counter < SubMenuDisplayedButtons)
                    {
                        currentSubmenyButtons[counter].Hide();
                        counter++;
                    }
                }
                SubMenuDisplayedButtons = 0;
                currentSubmenyButtons = getButtons(buttonType);
                for (int i = 0; i < currentSubmenyButtons.Length; i++)
                {
                    if (i == 9)
                    {
                        break;
                    }
                    currentSubmenyButtons[i].Show();
                    SubMenuDisplayedButtons++;
                }
            } 
        }
        public void ShowNextSubmenuButtons()
        {
            if (currentSubmenyButtons != null && SubMenuDisplayedButtons != currentSubmenyButtons.Length)
            {
                int n = SubMenuDisplayedButtons;
                int w = SubMenuDisplayedButtons;
                for (int i = n; i < currentSubmenyButtons.Length; i++)
                {
                    if (i == n + 9)
                    {
                        break;
                    }
                    currentSubmenyButtons[--w].Hide();
                    currentSubmenyButtons[i].Show();
                    SubMenuDisplayedButtons++;
                }
            }
        }
        public void ShowBackSubmenuButtons()
        {
            if (currentSubmenyButtons != null)
            {
                int n = SubMenuDisplayedButtons;
                int w = SubMenuDisplayedButtons;
                for (int i = n; i > 9; i--)
                {
                    if (i == n - 9)
                    {
                        break;
                    }
                    currentSubmenyButtons[--w].Hide();
                    currentSubmenyButtons[i-10].Show();
                    SubMenuDisplayedButtons--;
                }
            }
        }
        public void ChangeColorOfActiveButton(object sender)
        {
            if (ButtonColor ==null)
            {
                ButtonColor = sender;
                ((Button)ButtonColor).BackColor = System.Drawing.SystemColors.ActiveCaption;
                //
            }
            else if (Equals(sender,ButtonColor) == false)
            {
                ((Button)ButtonColor).UseVisualStyleBackColor = true;
                ButtonColor = sender;
                //ButtonColor = ((Button)sender).BackColor = System.Drawing.SystemColors.ActiveCaption;  // Why doesn't it work
                ((Button)ButtonColor).BackColor = System.Drawing.SystemColors.ActiveCaption;
            }
        }
    }
}
