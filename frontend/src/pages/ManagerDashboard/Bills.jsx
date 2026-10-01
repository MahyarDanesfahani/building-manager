import { useEffect, useState } from 'react';
import {
    Box, Button, Dialog, DialogTitle, DialogContent, DialogActions,
    TextField, Table, TableBody, TableCell, TableContainer, TableHead,
    TableRow, Paper, Typography, Chip, Accordion, AccordionSummary,
    AccordionDetails, CircularProgress
} from '@mui/material';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import { useAuth } from '../../context/AuthContext';
import { getBills, createBill, markSharePaid } from '../../api/billApi';

export default function Bills() {
    const { user } = useAuth();
    const [bills, setBills] = useState([]);
    const [loading, setLoading] = useState(true);
    const [openDialog, setOpenDialog] = useState(false);
    const [form, setForm] = useState({ title: '', totalAmount: '', issueDate: '', dueDate: '' });

    const loadBills = () => {
        setLoading(true);
        getBills(user.buildingId)
            .then((res) => setBills(res.data))
            .finally(() => setLoading(false));
    };

    useEffect(() => { loadBills(); }, []);

    const handleCreate = async () => {
        await createBill({ ...form, buildingId: user.buildingId, totalAmount: Number(form.totalAmount) });
        setOpenDialog(false);
        setForm({ title: '', totalAmount: '', issueDate: '', dueDate: '' });
        loadBills();
    };

    const handleMarkPaid = async (shareId) => {
        await markSharePaid(shareId);
        loadBills();
    };

    if (loading) return <CircularProgress />;

    return (
        <>
            <Box display="flex" justifyContent="space-between" alignItems="center" mb={2}>
                <Typography variant="h5">قبوض</Typography>
                <Button variant="contained" onClick={() => setOpenDialog(true)}>+ قبض جدید</Button>
            </Box>

            {bills.map((bill) => (
                <Accordion key={bill.billId}>
                    <AccordionSummary expandIcon={<ExpandMoreIcon />}>
                        <Box display="flex" justifyContent="space-between" width="100%" pr={2}>
                            <Typography>{bill.title}</Typography>
                            <Typography>{bill.totalAmount.toLocaleString()} تومان — سررسید: {bill.dueDate}</Typography>
                        </Box>
                    </AccordionSummary>
                    <AccordionDetails>
                        <TableContainer component={Paper}>
                            <Table size="small">
                                <TableHead>
                                    <TableRow>
                                        <TableCell>واحد</TableCell>
                                        <TableCell align="right">سهم</TableCell>
                                        <TableCell align="right">وضعیت</TableCell>
                                        <TableCell align="right">عملیات</TableCell>
                                    </TableRow>
                                </TableHead>
                                <TableBody>
                                    {bill.shares.map((share) => (
                                        <TableRow key={share.shareId}>
                                            <TableCell>{share.unitNumber}</TableCell>
                                            <TableCell align="right">{share.shareAmount.toLocaleString()} تومان</TableCell>
                                            <TableCell align="right">
                                                <Chip
                                                    label={share.paid ? 'پرداخت‌شده' : 'پرداخت‌نشده'}
                                                    color={share.paid ? 'success' : 'warning'}
                                                    size="small"
                                                />
                                            </TableCell>
                                            <TableCell align="right">
                                                {!share.paid && (
                                                    <Button size="small" onClick={() => handleMarkPaid(share.shareId)}>
                                                        علامت‌گذاری به‌عنوان پرداخت‌شده
                                                    </Button>
                                                )}
                                            </TableCell>
                                        </TableRow>
                                    ))}
                                </TableBody>
                            </Table>
                        </TableContainer>
                    </AccordionDetails>
                </Accordion>
            ))}

            <Dialog open={openDialog} onClose={() => setOpenDialog(false)} fullWidth maxWidth="sm">
                <DialogTitle>ثبت قبض جدید</DialogTitle>
                <DialogContent>
                    <TextField
                        label="عنوان قبض" fullWidth margin="normal"
                        value={form.title} onChange={(e) => setForm({ ...form, title: e.target.value })}
                    />
                    <TextField
                        label="مبلغ کل (تومان)" type="number" fullWidth margin="normal"
                        value={form.totalAmount} onChange={(e) => setForm({ ...form, totalAmount: e.target.value })}
                    />
                    <TextField
                        label="تاریخ صدور" type="date" fullWidth margin="normal" InputLabelProps={{ shrink: true }}
                        value={form.issueDate} onChange={(e) => setForm({ ...form, issueDate: e.target.value })}
                    />
                    <TextField
                        label="تاریخ سررسید" type="date" fullWidth margin="normal" InputLabelProps={{ shrink: true }}
                        value={form.dueDate} onChange={(e) => setForm({ ...form, dueDate: e.target.value })}
                    />
                </DialogContent>
                <DialogActions>
                    <Button onClick={() => setOpenDialog(false)}>انصراف</Button>
                    <Button variant="contained" onClick={handleCreate}>ثبت</Button>
                </DialogActions>
            </Dialog>
        </>
    );
}