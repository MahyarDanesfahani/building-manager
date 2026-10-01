import { useEffect, useState } from 'react';
import {
    Box, Button, Dialog, DialogTitle, DialogContent, DialogActions,
    TextField, Table, TableBody, TableCell, TableContainer, TableHead,
    TableRow, Paper, Typography, Chip, Accordion, AccordionSummary,
    AccordionDetails, CircularProgress
} from '@mui/material';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import { useAuth } from '../../context/AuthContext';
import { getMiscExpenses, createMiscExpense, markMiscSharePaid } from '../../api/miscApi';

export default function MiscExpenses() {
    const { user } = useAuth();
    const [expenses, setExpenses] = useState([]);
    const [loading, setLoading] = useState(true);
    const [openDialog, setOpenDialog] = useState(false);
    const [form, setForm] = useState({ title: '', description: '', amount: '', expenseDate: '' });

    const loadExpenses = () => {
        setLoading(true);
        getMiscExpenses(user.buildingId)
            .then((res) => setExpenses(res.data))
            .finally(() => setLoading(false));
    };

    useEffect(() => { loadExpenses(); }, []);

    const handleCreate = async () => {
        await createMiscExpense({ ...form, buildingId: user.buildingId, amount: Number(form.amount) });
        setOpenDialog(false);
        setForm({ title: '', description: '', amount: '', expenseDate: '' });
        loadExpenses();
    };

    const handleMarkPaid = async (shareId) => {
        await markMiscSharePaid(shareId);
        loadExpenses();
    };

    if (loading) return <CircularProgress />;

    return (
        <>
            <Box display="flex" justifyContent="space-between" alignItems="center" mb={2}>
                <Typography variant="h5">موارد جانبی</Typography>
                <Button variant="contained" onClick={() => setOpenDialog(true)}>+ مورد جدید</Button>
            </Box>

            {expenses.map((expense) => (
                <Accordion key={expense.expenseId}>
                    <AccordionSummary expandIcon={<ExpandMoreIcon />}>
                        <Box display="flex" justifyContent="space-between" width="100%" pr={2}>
                            <Typography>{expense.title}</Typography>
                            <Typography>{expense.amount.toLocaleString()} تومان — {expense.expenseDate}</Typography>
                        </Box>
                    </AccordionSummary>
                    <AccordionDetails>
                        {expense.description && (
                            <Typography variant="body2" color="text.secondary" mb={2}>{expense.description}</Typography>
                        )}
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
                                    {expense.shares.map((share) => (
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
                <DialogTitle>ثبت مورد جانبی جدید</DialogTitle>
                <DialogContent>
                    <TextField
                        label="عنوان" fullWidth margin="normal"
                        value={form.title} onChange={(e) => setForm({ ...form, title: e.target.value })}
                    />
                    <TextField
                        label="توضیحات" fullWidth margin="normal" multiline rows={2}
                        value={form.description} onChange={(e) => setForm({ ...form, description: e.target.value })}
                    />
                    <TextField
                        label="مبلغ (تومان)" type="number" fullWidth margin="normal"
                        value={form.amount} onChange={(e) => setForm({ ...form, amount: e.target.value })}
                    />
                    <TextField
                        label="تاریخ" type="date" fullWidth margin="normal" InputLabelProps={{ shrink: true }}
                        value={form.expenseDate} onChange={(e) => setForm({ ...form, expenseDate: e.target.value })}
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