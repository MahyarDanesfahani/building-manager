import { useEffect, useState } from 'react';
import {
    Box, Button, Dialog, DialogTitle, DialogContent, DialogActions,
    TextField, Table, TableBody, TableCell, TableContainer, TableHead,
    TableRow, Paper, Typography, Chip, Accordion, AccordionSummary,
    AccordionDetails, CircularProgress, MenuItem
} from '@mui/material';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import { useAuth } from '../../context/AuthContext';
import { getUnits, updateResident } from '../../api/memberApi';

export default function Members() {
    const { user } = useAuth();
    const [units, setUnits] = useState([]);
    const [loading, setLoading] = useState(true);
    const [editingResident, setEditingResident] = useState(null);
    const [form, setForm] = useState({});

    const loadUnits = () => {
        setLoading(true);
        getUnits(user.buildingId)
            .then((res) => setUnits(res.data))
            .finally(() => setLoading(false));
    };

    useEffect(() => { loadUnits(); }, []);

    const openEdit = (resident) => {
        setEditingResident(resident);
        setForm({
            fullName: resident.fullName,
            phoneNumber: resident.phoneNumber,
            role: resident.role,
            isOwner: resident.isOwner,
            residencyStartDate: resident.residencyStartDate || '',
            residencyEndDate: resident.residencyEndDate || '',
        });
    };

    const handleSave = async () => {
        await updateResident(editingResident.id, form);
        setEditingResident(null);
        loadUnits();
    };

    if (loading) return <CircularProgress />;

    return (
        <>
            <Typography variant="h5" mb={2}>اعضای ساختمان</Typography>

            {units.map((unit) => (
                <Accordion key={unit.unitId}>
                    <AccordionSummary expandIcon={<ExpandMoreIcon />}>
                        <Typography>واحد {unit.unitNumber} — کدپستی: {unit.postalCode} — تماس واحد: {unit.unitPhone}</Typography>
                    </AccordionSummary>
                    <AccordionDetails>
                        <TableContainer component={Paper}>
                            <Table size="small">
                                <TableHead>
                                    <TableRow>
                                        <TableCell>نام</TableCell>
                                        <TableCell>تلفن</TableCell>
                                        <TableCell>نقش</TableCell>
                                        <TableCell>مالک/مستاجر</TableCell>
                                        <TableCell>شروع سکونت</TableCell>
                                        <TableCell>پایان سکونت</TableCell>
                                        <TableCell align="right">عملیات</TableCell>
                                    </TableRow>
                                </TableHead>
                                <TableBody>
                                    {unit.residents.map((resident) => (
                                        <TableRow key={resident.id}>
                                            <TableCell>{resident.fullName}</TableCell>
                                            <TableCell>{resident.phoneNumber}</TableCell>
                                            <TableCell>
                                                <Chip label={resident.role === 'MANAGER' ? 'مدیر' : 'عضو'} size="small" />
                                            </TableCell>
                                            <TableCell>{resident.isOwner ? 'مالک' : 'مستاجر'}</TableCell>
                                            <TableCell>{resident.residencyStartDate || '—'}</TableCell>
                                            <TableCell>{resident.residencyEndDate || '—'}</TableCell>
                                            <TableCell align="right">
                                                <Button size="small" onClick={() => openEdit(resident)}>ویرایش</Button>
                                            </TableCell>
                                        </TableRow>
                                    ))}
                                </TableBody>
                            </Table>
                        </TableContainer>
                    </AccordionDetails>
                </Accordion>
            ))}

            <Dialog open={!!editingResident} onClose={() => setEditingResident(null)} fullWidth maxWidth="sm">
                <DialogTitle>ویرایش اطلاعات ساکن</DialogTitle>
                <DialogContent>
                    <TextField
                        label="نام کامل" fullWidth margin="normal"
                        value={form.fullName || ''} onChange={(e) => setForm({ ...form, fullName: e.target.value })}
                    />
                    <TextField
                        label="شماره تماس" fullWidth margin="normal"
                        value={form.phoneNumber || ''} onChange={(e) => setForm({ ...form, phoneNumber: e.target.value })}
                    />
                    <TextField
                        select label="نقش" fullWidth margin="normal"
                        value={form.role || ''} onChange={(e) => setForm({ ...form, role: e.target.value })}
                    >
                        <MenuItem value="MANAGER">مدیر</MenuItem>
                        <MenuItem value="MEMBER">عضو</MenuItem>
                    </TextField>
                    <TextField
                        select label="مالک یا مستاجر" fullWidth margin="normal"
                        value={form.isOwner ? 'true' : 'false'}
                        onChange={(e) => setForm({ ...form, isOwner: e.target.value === 'true' })}
                    >
                        <MenuItem value="true">مالک</MenuItem>
                        <MenuItem value="false">مستاجر</MenuItem>
                    </TextField>
                    <TextField
                        label="تاریخ شروع سکونت" type="date" fullWidth margin="normal" InputLabelProps={{ shrink: true }}
                        value={form.residencyStartDate || ''} onChange={(e) => setForm({ ...form, residencyStartDate: e.target.value })}
                    />
                    <TextField
                        label="تاریخ پایان سکونت" type="date" fullWidth margin="normal" InputLabelProps={{ shrink: true }}
                        value={form.residencyEndDate || ''} onChange={(e) => setForm({ ...form, residencyEndDate: e.target.value })}
                    />
                </DialogContent>
                <DialogActions>
                    <Button onClick={() => setEditingResident(null)}>انصراف</Button>
                    <Button variant="contained" onClick={handleSave}>ذخیره</Button>
                </DialogActions>
            </Dialog>
        </>
    );
}