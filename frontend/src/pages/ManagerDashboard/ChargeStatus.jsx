import { useEffect, useState } from 'react';
import {
    Table, TableBody, TableCell, TableContainer, TableHead,
    TableRow, Paper, Typography, CircularProgress
} from '@mui/material';
import api from '../../api/axiosConfig';
import { useAuth } from '../../context/AuthContext';

export default function ChargeStatus() {
    const { user } = useAuth();
    const [data, setData] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        api.get(`/charges/building/${user.buildingId}`)
            .then((res) => setData(res.data))
            .catch((err) => console.error(err))
            .finally(() => setLoading(false));
    }, [user.buildingId]);

    if (loading) return <CircularProgress />;

    return (
        <>
            <Typography variant="h5" mb={2}>وضعیت شارژ واحدها</Typography>
            <TableContainer component={Paper}>
                <Table>
                    <TableHead>
                        <TableRow>
                            <TableCell>شماره واحد</TableCell>
                            <TableCell align="right">مجموع واریزی</TableCell>
                            <TableCell align="right">مجموع هزینه‌شده</TableCell>
                            <TableCell align="right">مانده</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {data.map((row) => (
                            <TableRow key={row.unitId}>
                                <TableCell>{row.unitNumber}</TableCell>
                                <TableCell align="right">{row.totalDeposited.toLocaleString()} تومان</TableCell>
                                <TableCell align="right">{row.totalSpent.toLocaleString()} تومان</TableCell>
                                <TableCell align="right">{row.remaining.toLocaleString()} تومان</TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>
        </>
    );
}