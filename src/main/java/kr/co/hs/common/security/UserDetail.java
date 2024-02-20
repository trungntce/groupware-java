package kr.co.hs.common.security;

import kr.co.hs.emp.dto.EmpDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
public class UserDetail implements UserDetails {

	private static final long serialVersionUID = -8131959684520725990L;

	private List<GrantedAuthority> authorities;
	private EmpDTO emp;

	public UserDetail(EmpDTO _emp) {
		this.emp = _emp;
		authorities = new ArrayList<GrantedAuthority>();
		authorities.add( new SimpleGrantedAuthority("USE_YN_" + emp.getUseYn()));

		log.debug("emp UseYn ===============> {}", emp.getUseYn());
	}


	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public EmpDTO getEmp() {
		return this.emp;
	}

	public void setEmp(EmpDTO _emp) {
		this.emp = _emp;
	}

	@Override
	public String getPassword() {
		return emp.getEmpPw();
	}

	@Override
	public String getUsername() {
		return emp.getEmpId();
	}


	@Override
	public boolean isAccountNonExpired() {
		
		return true;
	}


	@Override
	public boolean isAccountNonLocked() {
		// User account is locked
		if( emp.getUseYn().equals("N") )
			return false;

		return true;
	}


	@Override
	public boolean isCredentialsNonExpired() {
		
		return true;
	}


	@Override
	public boolean isEnabled() {
		
		return emp.getUseYn().equals("Y");
	}


	@Override
	public int hashCode() {
		
		return emp.getEmpCd();
	}


	@Override
	public boolean equals(Object obj) {

		if( !(obj instanceof UserDetail ) ) {
			return false;
		}

		UserDetail detail = (UserDetail)obj;

		if( this.emp.getEmpCd() == null && detail.getEmp().getEmpCd() != null ) {
			return false;
		}

		if( this.emp.getEmpCd() != null && detail.getEmp().getEmpCd() == null ) {
			return false;
		}

		return true;
	}

}
