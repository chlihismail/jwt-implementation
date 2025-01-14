
interface InputProps{
    label?: string;
    value: string;
    placeholder?: string;
    onChange: (value: string) => void;
}

const PasswordInput: React.FC<InputProps> = ({label, value, placeholder, onChange}) => {
    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        onChange(e.target.value);
    }
    return(
        <div>
            {label && <label className="block mb-2 text-sm font-medium text-gray-700">{label}</label>}
            <input className="w-full border border-gray-300 rounded text-sm text-gray-700 pl-2 h-8 focus:border-2 focus:border-sky-950 focus:outline-none" type="password" value={value} placeholder={placeholder} onChange={handleChange}/>
        </div>
    );
}

export default PasswordInput;
