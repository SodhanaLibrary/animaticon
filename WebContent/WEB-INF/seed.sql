USE animaticon;

CREATE TABLE animation_controls_set (uuid VARCHAR(100), name VARCHAR(100),  animaticonObjName VARCHAR(100),  objJson TEXT, tags VARCHAR(300),  img TEXT, PRIMARY KEY(uuid));

CREATE TABLE animaticon_obj_sets (uuid VARCHAR(100), name VARCHAR(100),  animaticonObjName VARCHAR(100), tags VARCHAR(300),  objJson TEXT,  img TEXT, PRIMARY KEY(uuid));

CREATE TABLE animaticon_banner (uuid VARCHAR(100), name VARCHAR(100),  animaticonBannerName VARCHAR(100), tags VARCHAR(300),  objJson MEDIUMTEXT, img TEXT, PRIMARY KEY(uuid));

CREATE TABLE animaticon_banner (uuid VARCHAR(100), name VARCHAR(100),  animaticonBannerName VARCHAR(100), tags VARCHAR(300),  objJson MEDIUMTEXT, img TEXT, PRIMARY KEY(uuid));

CREATE TABLE entitlements (name VARCHAR(100), description VARCHAR(100), PRIMARY KEY(name));

CREATE TABLE roles (name VARCHAR(100), description VARCHAR(100), PRIMARY KEY(name));

CREATE TABLE role_entitlements (role VARCHAR(100), entitlement VARCHAR(100), PRIMARY KEY(role, entitlement), FOREIGN KEY(role) REFERENCES roles(name), FOREIGN KEY(entitlement) REFERENCES entitlements(name));

CREATE TABLE users (uuid VARCHAR(100), name VARCHAR(100),  email VARCHAR(200), PRIMARY KEY(uuid));

CREATE TABLE user_roles (user_id VARCHAR(100), role VARCHAR(100), PRIMARY KEY(user_id, role), FOREIGN KEY(role) REFERENCES roles(name), FOREIGN KEY(user_id) REFERENCES users(uuid));

INSERT INTO roles (name, description)  VALUES ('Open User', 'Un Authenticated user');
INSERT INTO roles (name, description)  VALUES ('Logged In User', 'Authenticated user');
INSERT INTO roles (name, description)  VALUES ('Admin', 'Administrative access');
INSERT INTO roles (name, description)  VALUES ('Super Admin', 'Super Administrative access');


INSERT INTO entitlements (name, description)  VALUES ('dmars.banners.browse', 'browse banners');
INSERT INTO entitlements (name, description)  VALUES ('dmars.banners.create', 'create banners');
INSERT INTO entitlements (name, description)  VALUES ('dmars.banners.update', 'update banners');
INSERT INTO entitlements (name, description)  VALUES ('dmars.banners.delete', 'delete banners');

INSERT INTO entitlements (name, description)  VALUES ('dmars.icons.browse', 'browse icons');
INSERT INTO entitlements (name, description)  VALUES ('dmars.icons.create', 'create icons');
INSERT INTO entitlements (name, description)  VALUES ('dmars.icons.update', 'update icons');
INSERT INTO entitlements (name, description)  VALUES ('dmars.icons.delete', 'delete icons');

INSERT INTO entitlements (name, description)  VALUES ('dmars.animation.defined.browse', 'browse pre-defined animations');
INSERT INTO entitlements (name, description)  VALUES ('dmars.animation.defined.create', 'create pre-defined animations');
INSERT INTO entitlements (name, description)  VALUES ('dmars.animation.defined.update', 'update pre-defined animations');
INSERT INTO entitlements (name, description)  VALUES ('dmars.animation.defined.delete', 'delete pre-defined animations');

INSERT INTO entitlements (name, description)  VALUES ('dmars.export.img', 'export to Image');
INSERT INTO entitlements (name, description)  VALUES ('dmars.export.js', 'export to JS');
INSERT INTO entitlements (name, description)  VALUES ('dmars.export.video', 'export to Video');
INSERT INTO entitlements (name, description)  VALUES ('dmars.export.gif', 'export to GIF');


INSERT INTO role_entitlements (role, entitlement) VALUES ('Open User', 'dmars.banners.browse');
INSERT INTO role_entitlements (role, entitlement) VALUES ('Open User', 'dmars.icons.browse');
INSERT INTO role_entitlements (role, entitlement) VALUES ('Open User', 'dmars.animation.defined.browse');
INSERT INTO role_entitlements (role, entitlement) VALUES ('Open User', 'dmars.export.img');

INSERT INTO role_entitlements (role, entitlement) VALUES ('Logged In User', 'dmars.banners.browse');
INSERT INTO role_entitlements (role, entitlement) VALUES ('Logged In User', 'dmars.icons.browse');
INSERT INTO role_entitlements (role, entitlement) VALUES ('Logged In User', 'dmars.animation.defined.browse');
INSERT INTO role_entitlements (role, entitlement) VALUES ('Logged In User', 'dmars.export.img');

INSERT INTO role_entitlements (role, entitlement) VALUES ('Admin', 'dmars.banners.browse');
INSERT INTO role_entitlements (role, entitlement) VALUES ('Admin', 'dmars.icons.browse');
INSERT INTO role_entitlements (role, entitlement) VALUES ('Admin', 'dmars.animation.defined.browse');
INSERT INTO role_entitlements (role, entitlement) VALUES ('Admin', 'dmars.export.img');
INSERT INTO role_entitlements (role, entitlement) VALUES ('Admin', 'dmars.banners.create');
INSERT INTO role_entitlements (role, entitlement) VALUES ('Admin', 'dmars.icons.create');
INSERT INTO role_entitlements (role, entitlement) VALUES ('Admin', 'dmars.animation.defined.create');

INSERT INTO role_entitlements (role, entitlement) VALUES ('Super Admin', 'dmars.banners.browse');
INSERT INTO role_entitlements (role, entitlement) VALUES ('Super Admin', 'dmars.icons.browse');
INSERT INTO role_entitlements (role, entitlement) VALUES ('Super Admin', 'dmars.animation.defined.browse');
INSERT INTO role_entitlements (role, entitlement) VALUES ('Super Admin', 'dmars.export.img');
INSERT INTO role_entitlements (role, entitlement) VALUES ('Super Admin', 'dmars.banners.create');
INSERT INTO role_entitlements (role, entitlement) VALUES ('Super Admin', 'dmars.icons.create');
INSERT INTO role_entitlements (role, entitlement) VALUES ('Super Admin', 'dmars.animation.defined.create');
INSERT INTO role_entitlements (role, entitlement) VALUES ('Super Admin', 'dmars.banners.update');
INSERT INTO role_entitlements (role, entitlement) VALUES ('Super Admin', 'dmars.icons.update');
INSERT INTO role_entitlements (role, entitlement) VALUES ('Super Admin', 'dmars.animation.defined.update');
INSERT INTO role_entitlements (role, entitlement) VALUES ('Super Admin', 'dmars.banners.delete');
INSERT INTO role_entitlements (role, entitlement) VALUES ('Super Admin', 'dmars.icons.delete');
INSERT INTO role_entitlements (role, entitlement) VALUES ('Super Admin', 'dmars.animation.defined.delete');


INSERT INTO users (uuid, name, email) VALUES ('00000001-0000-0000-0000-000000000001', 'Srinivas', 'sodhanaware@gmail.com');
INSERT INTO users (uuid, name, email) VALUES ('00000001-0000-0000-0000-000000000002', 'Aswani', 'doodlemarsofficial@gmail.com');
INSERT INTO users (uuid, name, email) VALUES ('00000001-0000-0000-0000-000000000003', 'Srinivas', 'srinivas.dasari1990@gmail.com');
INSERT INTO users (uuid, name, email) VALUES ('00000001-0000-0000-0000-000000000004', 'Srinivas', 'admin@sodhanalibrary@gmail.com');


INSERT INTO user_roles (user_id, role) VALUES ('00000001-0000-0000-0000-000000000001', 'Super Admin');
INSERT INTO user_roles (user_id, role) VALUES ('00000001-0000-0000-0000-000000000003', 'Super Admin');
INSERT INTO user_roles (user_id, role) VALUES ('00000001-0000-0000-0000-000000000004', 'Super Admin');
INSERT INTO user_roles (user_id, role) VALUES ('00000001-0000-0000-0000-000000000002', 'Admin');

