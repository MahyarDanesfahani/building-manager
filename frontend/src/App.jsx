import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import { AuthProvider, useAuth } from './context/AuthContext';
import Login from './pages/Login/Login';
import ManagerLayout from './pages/ManagerDashboard/ManagerLayout';
import ChargeStatus from './pages/ManagerDashboard/ChargeStatus';
import MemberDashboard from './pages/MemberDashboard/MemberDashboard';
import Bills from './pages/ManagerDashboard/Bills';
import MiscExpenses from './pages/ManagerDashboard/MiscExpenses';
import Members from './pages/ManagerDashboard/Members';
function PrivateRoute({ children, allowedRole }) {
    const { user } = useAuth();
    if (!user) return <Navigate to="/login" />;
    if (allowedRole && user.role !== allowedRole) return <Navigate to="/login" />;
    return children;
}

function App() {
    return (
        <AuthProvider>
            <BrowserRouter>
                <Routes>
                    <Route path="/login" element={<Login />} />

                    <Route
                        path="/manager"
                        element={
                            <PrivateRoute allowedRole="MANAGER">
                                <ManagerLayout />
                            </PrivateRoute>
                        }
                    >
                        <Route path="members" element={<Members />} />
                        <Route path="misc" element={<MiscExpenses />} />
                        <Route path="bills" element={<Bills />} />
                        <Route index element={<ChargeStatus />} />
                        <Route path="charges" element={<ChargeStatus />} />
                    </Route>

                    <Route
                        path="/member"
                        element={
                            <PrivateRoute allowedRole="MEMBER">
                                <MemberDashboard />
                            </PrivateRoute>
                        }
                    />

                    <Route path="*" element={<Navigate to="/login" />} />
                </Routes>
            </BrowserRouter>
        </AuthProvider>
    );
}

export default App;
