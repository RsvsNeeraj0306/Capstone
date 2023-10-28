import React, { useState, useEffect } from 'react';
import { toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

const NetworkDeviceRMAForm = () => {
    const [networkDeviceId, setNetworkDeviceId] = useState('');
    const [actionType, setActionType] = useState('Replace'); // Default to 'Replace'
    const [amount, setAmount] = useState('');
    const [dateOfAction, setDateOfAction] = useState('');
    const [reason, setReason] = useState('');

    const [networkDevices, setNetworkDevices] = useState([]);

    const resetForm = () => {
        setNetworkDeviceId('');
        setActionType(''); // Reset to 'Replace'
        setAmount('');
        setDateOfAction('');
        setReason('');
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        const rmaData = {
            networkDevice: {
                id: networkDeviceId,
            },
            networkDeviceRMA: {
                actionType,
                amount,
                dateOfAction,
                reason,
            },
        };

        try {
            const response = await fetch('http://localhost:8080/api/setRMA', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer ' + localStorage.getItem('token')
                },
                body: JSON.stringify(rmaData),
            });

            if (response.ok) {
                const data = await response.json();
                toast.success('Network device RMA added successfully!');
            } else {
                toast.error('Error')
            }
        } catch (error) {
            toast.error('Error')
        }

        resetForm();
        console.log(rmaData);
    }

    useEffect(() => {
        fetch('http://localhost:8080/api/allHardware', {
            headers: {
                'Authorization': 'Bearer ' + localStorage.getItem('token')
            }
        })
            .then((response) => response.json())
            .then((data) => {
                setNetworkDevices(data);
            })
            .catch((error) => console.error('Error fetching network devices: ', error));
    });

    return (
        <div className="form-container">
            <h2>Network Device RMA</h2>
            <form onSubmit={handleSubmit}>
                <label>
                    Network Device ID:
                    <select
                        value={networkDeviceId}
                        onChange={(e) => setNetworkDeviceId(e.target.value)}
                    >
                        <option value="">Select a network device</option>
                        {networkDevices.map((networkDevice) => (
                            <option key={networkDevice.id} value={networkDevice.id}>
                                {networkDevice.id}
                            </option>
                        ))}
                    </select>
                </label>
                <br />
                <br />
                <label>
                    Action Type:
                    <select
                        value={actionType}
                        onChange={(e) => setActionType(e.target.value)}
                    >
                        <option value="Replace">Replace</option>
                        <option value="Refund">Refund</option>
                        <option value="Repair">Repair</option>
                    </select>
                </label>
                <label>
                    <br />
                    <br />
                    Amount:
                    <input
                        type="text"
                        value={amount}
                        onChange={(e) => setAmount(e.target.value)}
                    />
                </label>
                <label>
                    Date of Action:
                    <input
                        type="date"
                        value={dateOfAction}
                        onChange={(e) => setDateOfAction(e.target.value)}
                    />
                </label>
                <label>
                    Reason:
                    <input
                        type="text"
                        value={reason}
                        onChange={(e) => setReason(e.target.value)}
                    />
                </label>
                <br />
                 <br />
                <button type="submit">Set Network Device RMA</button>
            </form>
        </div>
    );
};

export default NetworkDeviceRMAForm;
