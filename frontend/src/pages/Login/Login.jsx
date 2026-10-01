import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { TextField, Button, Box, Typography, Alert, Paper } from '@mui/material';
import api from '../../api/axiosConfig';
import { useAuth } from '../../context/AuthContext';

export default function Login() {
    const [postalCode, setPostalCode] = useState('');
    const [nationalCode, setNationalCode] = useState('');
    const [error, setError] = useState('');
    const { login } = useAuth();
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError('');
        try {
            const response = await api.post('/auth/login', { postalCode, nationalCode });
            login(response.data);

            if (response.data.role === 'MANAGER') {
                navigate('/manager');
            } else {
                navigate('/member');
            }
        } catch (err) {
            setError('کدپستی یا کد ملی اشتباه است');
        }
    };

    return (
        <Box display="flex" justifyContent="center" alignItems="center" minHeight="100vh" bgcolor="#f5f5f5">
            <Paper elevation={3} sx={{ p: 4, width: 350 }}>
                <Typography variant="h5" mb={3} textAlign="center">ورود به سامانه ساختمان</Typography>

                {error && <Alert severity="error" sx={{ mb: 2 }}>{error}</Alert>}

                <form onSubmit={handleSubmit}>
                    <TextField
                        label="کدپستی"
                        fullWidth
                        margin="normal"
                        value={postalCode}
                        onChange={(e) => setPostalCode(e.target.value)}
                    />
                    <TextField
                        label="کد ملی"
                        type="password"
                        fullWidth
                        margin="normal"
                        value={nationalCode}
                        onChange={(e) => setNationalCode(e.target.value)}
                    />
                    <Button type="submit" variant="contained" fullWidth sx={{ mt: 3 }}>
                        ورود
                    </Button>
                </form>
            </Paper>
        </Box>
    );
}