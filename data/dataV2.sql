CREATE DATABASE UserManagement

GO

USE UserManagement

GO

CREATE TABLE [dbo].[tblUsers]
(
    [userID] NVARCHAR (50) NOT NULL,
    [fullName] NVARCHAR (50) NULL,
    [password] NVARCHAR (255) NULL,
    [roleID] NVARCHAR (50) NULL,
    [status] BIT NULL,
    [email] NVARCHAR (50) NULL,
    CONSTRAINT [PK_tblUsers] PRIMARY KEY CLUSTERED ([userID] ASC)
    );

CREATE TABLE [dbo].[tblProducts]
(
    [productID] NVARCHAR (50) NOT NULL,
    [productName] NVARCHAR (50) NULL,
    [price] FLOAT (53) NOT NULL,
    [quantity] FLOAT (53) NOT NULL,
    [status] BIT NULL,
    [imageUrl] NVARCHAR (255) NULL,
    CONSTRAINT [PK_tblProducts] PRIMARY KEY CLUSTERED ([productID] ASC)
    );

CREATE TABLE [dbo].[tblOrders]
(
    [orderID] INT NOT NULL,
    [userID] NVARCHAR (50) NULL,
    [total] FLOAT (53) NOT NULL,
    [date] DATE NOT NULL,
    CONSTRAINT [PK_tblOrders] PRIMARY KEY CLUSTERED ([orderID] ASC),
    CONSTRAINT [FK_tblOrders_tblUsers] FOREIGN KEY ([userID]) REFERENCES [dbo].[tblUsers] ([userID])
    );

CREATE TABLE [dbo].[tblOrderDetail]
(
    [orderID] INT NOT NULL,
    [productID] NVARCHAR (50) NOT NULL,
    [price] FLOAT (53) NOT NULL,
    [quantity] INT NOT NULL,
    CONSTRAINT [PK_tblOrderDetail] PRIMARY KEY CLUSTERED ([orderID] ASC, [productID] ASC),
    CONSTRAINT [FK_tblOrderDetail_tblOrders] FOREIGN KEY ([orderID]) REFERENCES [dbo].[tblOrders] ([orderID]),
    CONSTRAINT [FK_tblOrderDetail_tblProducts] FOREIGN KEY ([productID]) REFERENCES [dbo].[tblProducts] ([productID])
    );

INSERT INTO [UserManagement].[dbo].[tblUsers]
([userID], [fullName], [password], [roleID], [status], [email])
VALUES
    ('admin', 'Tao la ad', '$31$16$Yz81Pn2cR3Sax2gWu7A3yQ8xNlguDznePZ22ZnPxCyM', 'AD', 1, NULL),
    ('deeptry', 'Anh bay den day', '$31$16$Yz81Pn2cR3Sax2gWu7A3yQ8xNlguDznePZ22ZnPxCyM', 'US', NULL, 'manhduonglhp4@gmail.com'),
    ('deeptry2', 'Duong Nguyen', '$31$16$Yz81Pn2cR3Sax2gWu7A3yQ8xNlguDznePZ22ZnPxCyM', 'US', NULL, 'manhduonglhp1@gmail.com'),
    ('deeptry3', 'Duong Nguyen Manh', '$31$16$Yz81Pn2cR3Sax2gWu7A3yQ8xNlguDznePZ22ZnPxCyM', 'US', NULL, 'manhduonglhp2@gmail.com'),
    ('duma', 'assmin', '$31$16$Yz81Pn2cR3Sax2gWu7A3yQ8xNlguDznePZ22ZnPxCyM', 'AD', NULL, NULL),
    ('duong22', 'assmin', '$31$16$Yz81Pn2cR3Sax2gWu7A3yQ8xNlguDznePZ22ZnPxCyM', 'US', NULL, NULL),
    ('Hoadnt', 'Hoa Doan', '$31$16$Yz81Pn2cR3Sax2gWu7A3yQ8xNlguDznePZ22ZnPxCyM', 'AD', NULL, NULL),
    ('SE130363', 'Ngo Ha Tri Bao', '$31$16$Yz81Pn2cR3Sax2gWu7A3yQ8xNlguDznePZ22ZnPxCyM', 'AD', NULL, NULL),
    ('SE140103', 'Phuoc Ha', '$31$16$Yz81Pn2cR3Sax2gWu7A3yQ8xNlguDznePZ22ZnPxCyM', 'AD', NULL, NULL),
    ('SE140119', 'Trai Nguyen', '$31$16$Yz81Pn2cR3Sax2gWu7A3yQ8xNlguDznePZ22ZnPxCyM', 'AD', NULL, NULL),
    ('SE140130', 'Tam Tran', '$31$16$Yz81Pn2cR3Sax2gWu7A3yQ8xNlguDznePZ22ZnPxCyM', 'AD', NULL, NULL),
    ('user1', 'user1', '$31$16$Yz81Pn2cR3Sax2gWu7A3yQ8xNlguDznePZ22ZnPxCyM', 'US', 1, NULL),
    ('user2', 'user2', '$31$16$Yz81Pn2cR3Sax2gWu7A3yQ8xNlguDznePZ22ZnPxCyM', 'US', NULL, NULL);

INSERT INTO [UserManagement].[dbo].[tblProducts]
([productID], [productName], [price], [quantity], [status], [imageUrl])
VALUES
    ('P001', 'Gucci Bag', 500, 0, 1, 'img/gucci_bag.jpg'),
    ('P002', 'Luon Vui Tuoi T-Shirt', 750, 5, 1, 'img/luonvuituoi.jpg'),
    ('P003', 'Mini Skrrr', 250, 20, 1, 'img/mini_skirt.jpg'),
    ('P004', 'Short Skirt', 300, 30, 1, 'img/short_skirt.jpg'),
    ('P005', 'Short T-Shirt', 150, 40, 1, 'img/short_t_shirt.jpg'),
    ('P006', 'Long Skirt', 400, 50, 1, 'img/long_skirt.jpg'),
    ('P007', 'Croptop Shirt', 250, 15, 1, 'img/croptop_shirt.jpg'),
    ('P008', 'Denim Jacket', 89.99, 50, 1, 'img/denim_jacket.jpg'),
    ('P009', 'Summer Dress', 59.99, 75, 1, 'img/summer_dress.jpg'),
    ('P010', 'Leather Boots', 129.99, 30, 1, 'img/leather_boots.jpg'),
    ('P011', 'Silk Scarf', 29.99, 100, 1, 'img/silk_scarf.jpg'),
    ('P012', 'Sports Sneakers', 79.99, 60, 1, 'img/sports_sneakers.jpg');

