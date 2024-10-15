package com.univpoitiers.bubellea.activities;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;


import com.univpoitiers.bubellea.allergies.AllergiesFragment;
import com.univpoitiers.bubellea.budget.BudgetPreferenceFragment;
import com.univpoitiers.bubellea.drinkcomplexity.DrinkComplexityFragment;
import com.univpoitiers.bubellea.drinkgoal.DrinkGoalFragment;
import com.univpoitiers.bubellea.fruityflavorpreferences.FruityAndTexturePreferenceFragment;
import com.univpoitiers.bubellea.sugarpreferences.PreferencesFragment;
import com.univpoitiers.bubellea.teapreferences.TeaPreferencesFragment;
import com.univpoitiers.bubellea.texturepreference.CombinedPreferenceFragment;


public class FormPagerAdapter extends FragmentStateAdapter {

    private Fragment[] fragments; // Ajouter un tableau pour garder les références des fragments
    ViewPager2 viewPager;

    public FormPagerAdapter(@NonNull FragmentActivity fragmentActivity, ViewPager2 viewPager) {
        super(fragmentActivity);
        this.viewPager = viewPager;
        // Initialiser le tableau avec les fragments que tu veux afficher
        fragments = new Fragment[] {
                new TeaPreferencesFragment(),
                new FruityAndTexturePreferenceFragment(),
                new PreferencesFragment(),
                new CombinedPreferenceFragment(),
                new AllergiesFragment(),
                new DrinkComplexityFragment(),
                new BudgetPreferenceFragment(),
                new DrinkGoalFragment()
        };
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = fragments[position];
        if (fragment instanceof TeaPreferencesFragment) {
            ((TeaPreferencesFragment) fragment).setViewPager(viewPager);
        } else if (fragment instanceof FruityAndTexturePreferenceFragment) {
            ((FruityAndTexturePreferenceFragment) fragment).setViewPager(viewPager);
        } else if (fragment instanceof PreferencesFragment) {
            ((PreferencesFragment) fragment).setViewPager(viewPager);
        } else if (fragment instanceof CombinedPreferenceFragment) {
            ((CombinedPreferenceFragment) fragment).setViewPager(viewPager);
        } else if (fragment instanceof AllergiesFragment) {
            ((AllergiesFragment) fragment).setViewPager(viewPager);
        } else if (fragment instanceof DrinkComplexityFragment) {
            ((DrinkComplexityFragment) fragment).setViewPager(viewPager);
        } else if (fragment instanceof BudgetPreferenceFragment) {
            ((BudgetPreferenceFragment) fragment).setViewPager(viewPager);
        } else if (fragment instanceof DrinkGoalFragment) {
            ((DrinkGoalFragment) fragment).setViewPager(viewPager);
        }
        return fragment;
    }

    public Fragment getFragmentAt(int position) {
        // Retourner le fragment déjà existant à cette position
        return fragments[position];
    }

    @Override
    public int getItemCount() {
        return fragments.length; // Retourner le nombre de fragments
    }
}
