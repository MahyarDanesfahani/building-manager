import { useState } from 'react';
import { Outlet, useNavigate, useLocation } from 'react-router-dom';
import {
    Drawer, List, ListItemButton, ListItemText, AppBar, Toolbar,
    Typography, Box, Button
} from '@mui/material';
import { useAuth } from '../../context/AuthContext';

const drawerWidth = 240;

const menuItems = [
    { text: 'وضعیت شارژ', path: '/manager/charges' },
    { text: 'قبوض', path: '/manager/bills' },
    { text: 'موارد جانبی', path: '/manager/misc' },
    { text: 'اعضا', path: '/manager/members' },
];

export default function ManagerLayout() {
    const navigate = useNavigate();
    const location = useLocation();
    const { user, logout } = useAuth();

    const handleLogout = () => {
        logout();
        navigate('/login');
    };

    return (
        <Box sx={{ display: 'flex' }}>
            <AppBar position="fixed" sx={{ zIndex: 1201 }}>
                <Toolbar sx={{ justifyContent: 'space-between' }}>
                    <Typography variant="h6">پنل مدیر — خوش آمدید {user?.fullName}</Typography>
                    <Button color="inherit" onClick={handleLogout}>خروج</Button>
                </Toolbar>
            </AppBar>

            <Drawer
                variant="permanent"
                anchor="right"
                sx={{
                    width: drawerWidth,
                    flexShrink: 0,
                    [`& .MuiDrawer-paper`]: { width: drawerWidth, boxSizing: 'border-box' },
                }}
            >
                <Toolbar />
                <List>
                    {menuItems.map((item) => (
                        <ListItemButton
                            key={item.path}
                            selected={location.pathname === item.path}
                            onClick={() => navigate(item.path)}
                        >
                            <ListItemText primary={item.text} />
                        </ListItemButton>
                    ))}
                </List>
            </Drawer>

            <Box component="main" sx={{ flexGrow: 1, p: 3, mt: 8 }}>
                <Outlet />
            </Box>
        </Box>
    );
}